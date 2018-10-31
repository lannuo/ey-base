package cn.sky999.aspect;

import cn.sky999.baseEntity.Log;
import cn.sky999.common.exception.ExceptionUtil;
import cn.sky999.common.loginCache.LoginCache;
import cn.sky999.common.request.IpUtil;
import cn.sky999.service.LogServiceIntFc;
import cn.sky999.util.Const;
import cn.sky999.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class LogAspect {

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	LogServiceIntFc logService;

	private Log log;

	private HttpServletRequest request;

	/**
	 * 前置通知<br>
	 * 在目标类的方法执行之前执行<br>
	 * 应用：可以对方法的参数来做校验
	 * 
	 * @param joinPoint
	 */
	public void before(JoinPoint joinPoint,LogAnnotation logAnnotation) {
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = IpUtil.getIp(request);
		log = new Log(ip, request.getRequestURI());
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			token = request.getParameter("token");
		}
		if (StringUtils.isBlank(token)) {
			return ;
		}
		try {
			Object o = RedisUtil.redisGet(token);
			if (o != null) {
				LoginCache lc = (LoginCache)o;
				log.setUserId(lc.getUser().get("userId")+"");
				log.setUserName(lc.getUser().get("loginName")+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最终通知<br>
	 * 在目标类的方法执行之后执行，如果程序出现了异常，最终通知也会执行<br>
	 * 应用：例如像释放资源
	 * 
	 * @param joinPoint
	 */
	public void after(JoinPoint joinPoint,LogAnnotation logAnnotation) {
		Log newLog = new Log();
		newLog.setIp(log.getIp());
		newLog.setUserId(log.getUserId());
		newLog.setUserName(log.getUserName());
		newLog.setAction(log.getAction());
		newLog.setStatus(log.getStatus());
		newLog.setStartTime(log.getStartTime());
		newLog.setContent(log.getContent());
		newLog.setEndTime(new Date());
		try {
			logService.saveEntity(newLog);
		} catch (Exception e) {
			logger.error(log.getContent());
			logger.error(Const.MSG_CH_LOG_KEEP_ERROR, e);
		}

	}

	/**
	 * 后置通知<br>
	 * 方法正常执行后的通知<br>
	 * 应用：可以修改方法的返回值
	 * 
	 * @param joinPoint
	 * @param result
	 */
	public void afterReturning(JoinPoint joinPoint, Object result,LogAnnotation logAnnotation) {

	}

	/**
	 * 异常抛出通知<br>
	 * 应用：包装异常的信息
	 * 
	 * @param joinPoint
	 * @param ex
	 */
	public void afterThrowing(JoinPoint joinPoint, Exception ex,LogAnnotation logAnnotation) {
		log.setStatus(1);
		log.setContent(ExceptionUtil.getBytes(ex, "GBK"));// jdk运行编码
	}

	/**
	 * 环绕通知<br>
	 * 方法的执行前后执行<br>
	 * 注意：目标的方法默认不执行，需要使用ProceedingJoinPoint对来让目标对象的方法执行。
	 * 
	 * @param joinPoint
	 */
	public void around(ProceedingJoinPoint joinPoint) {
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
