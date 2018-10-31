package cn.sky999.controller;

import cn.sky999.aspect.LogAnnotation;
import cn.sky999.common.loginCache.LoginCache;
import cn.sky999.editor.*;
import cn.sky999.editor.MultipleDateEditor;
import cn.sky999.util.Const;
import cn.sky999.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new MultipleDateEditor("yyyy-MM-dd HH:mm:ss", new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" }, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(float.class, new FloatEditor());
	}

	/**
	 * 获取request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	/**
	 * 获取登录后的缓存信息
	 * @return
	 */
	public LoginCache getLoginCache() throws Exception{
		LoginCache loginCache = (LoginCache) RedisUtil.redisGet(this.getToken());
		if(loginCache!=null){
			return loginCache;
		}else{
			throw new LoginException(Const.MSG_CH_AUTH_ASK_LOGIN);
		}
	}

	/**
	 * 获取当前用户
	 * @return
	 */
	public String getToken() throws Exception{
		String token = getRequest().getHeader("token");
		if (StringUtils.isBlank(token))
			token = getRequest().getParameter("token");
		if (StringUtils.isBlank(token))
			throw new LoginException();
		return token;
	}
	/**
	 * 异常信息捕获处理
	 * @param ex
	 * @param request
	 * @return
	 */
	@LogAnnotation
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex, HttpServletRequest request,HttpServletResponse response)throws Exception{
		throw ex;
	}
}
