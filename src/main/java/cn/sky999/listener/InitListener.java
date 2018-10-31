package cn.sky999.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent context) {
//		SpringUtils.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(context.getServletContext()));
//		ConfigServiceIntFc configService = SpringUtils.getBean("configService");
//		CodeInfoServiceIntFc codeInfoService = SpringUtils.getBean("codeInfoService");
//		configService.redisSet();
//		codeInfoService.redisSet();
	}

	public void contextDestroyed(ServletContextEvent context) {

	}
}