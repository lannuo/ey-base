package cn.sky999.interceptor;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sky999.common.loginCache.LoginCache;
import cn.sky999.common.response.OperInfo;
import cn.sky999.common.response.RespCode;
import cn.sky999.util.Const;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.sky999.aspect.Authority;
import cn.sky999.aspect.AuthorityType;
import cn.sky999.util.RedisUtil;

public class HandlerInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 免登入 免检查地址
	 */
	private List<String> uncheckUrls;

	/**
	 * 在业务处理器处理请求之前被调用 <br>
	 * 如果返回false,从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 <br>
	 * 如果返回true,执行下一个拦截器,直到所有的拦截器都执行完毕<br>
	 * 再执行被拦截的Controller <br>
	 * 然后进入拦截器链, <br>
	 * 从最后一个拦截器往回执行所有的postHandle() <br>
	 * 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		String requestUri = request.getRequestURI();
		if(request.getMethod().equals("OPTIONS"))
			return true;
//		if(requestUri.length()>0){
//			return true;
//		}
		for (String url : uncheckUrls) {
			if (requestUri.contains(url)) {
				return true;
			}
		}
		// aop验证
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Class<?> clazz = hm.getBeanType();
			Method m = hm.getMethod();
			if (clazz != null && m != null) {
				boolean isClzAnnotation = clazz.isAnnotationPresent(Authority.class);
				boolean isMethondAnnotation = m.isAnnotationPresent(Authority.class);
				Authority authority = null;
				if (isMethondAnnotation) {
					authority = m.getAnnotation(Authority.class);
				} else if (isClzAnnotation) {
					authority = clazz.getAnnotation(Authority.class);
				} else {
					authority = new Authority() {
						@Override
						public Class<? extends Annotation> annotationType() {
							return null;
						}
						@Override
						public AuthorityType value() {
							return AuthorityType.NoAuthority;
						}
					};
				}
				if (authority != null) {
					if (AuthorityType.NoValidate == authority.value()) {// ----------------------不验证
						return true;
					} else if (AuthorityType.NoAuthority == authority.value()) {// --------------验证是否登录
						if( validateLogin(request)){
							return true;
						}else{
							return errorVerify(response,Const.MSG_CH_AUTH_TIME_OUT, RespCode.NO_AUTH);
						}
					} else {// ------------------------------------------------------------------验证登录及权限
						if (validateRole(hm,clazz,request)) {
							return true;
						}else{
							return errorVerify(response, Const.MSG_CH_AUTH_NO_PERMISSIONS,RespCode.NO_PERMISSIONS);
						}
					}
				}
			}
			return errorVerify(response, Const.MSG_CH_AUTH_TIME_OUT, RespCode.NO_AUTH);
		} else {
			return false;
		}
	}

	private boolean errorVerify(HttpServletResponse response, String msg, int errCode) throws Exception {
		OperInfo o = new OperInfo();
		o.setMsg(msg);
		o.setOk(false);
		o.setCode(errCode);
		ObjectMapper mapper = new ObjectMapper();
		this.returnJson(response, mapper.writeValueAsString(o));
		return false;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	private void returnJson(HttpServletResponse response, String json) throws Exception {
		this.addHeader(response);
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		writer = response.getWriter();
		writer.print(json);
		if (writer != null)
			writer.close();
	}

	public void addHeader(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,token");
		response.addHeader("Access-Control-Max-Age", "600000");
	}

	// 登录验证
	public boolean validateLogin(HttpServletRequest request) throws Exception{
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			token = request.getParameter("token");
		}
		if (StringUtils.isBlank(token)) {
			return false;
		}
		Object o = RedisUtil.redisGet(token);
		if (o != null) {
			RedisUtil.redisSetLiveTime(token, 1800);
			return true;
		}
		return false;
	}

	// 权限验证
	public boolean validateRole(HandlerMethod hm, Class<?> clazz, HttpServletRequest request)throws Exception {
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			token = request.getParameter("token");
		}
		if (StringUtils.isBlank(token)) {
			return false;
		}
		Object o = RedisUtil.redisGet(token);
		if(o!=null){
			String mname = hm.getMethod().getName();
			String cname = clazz.getSimpleName();
			LoginCache loginCache=(LoginCache)o;
			List<String> mls=loginCache.getRoleAuthority().get(cname);
			if(mls!=null&&mls.size()>0){
				for(String s:mls){
					if(mname.equals(s)){
						RedisUtil.redisSetLiveTime(token, 1800);
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<String> getUncheckUrls() {
		return uncheckUrls;
	}

	public void setUncheckUrls(List<String> uncheckUrls) {
		this.uncheckUrls = uncheckUrls;
	}

}
