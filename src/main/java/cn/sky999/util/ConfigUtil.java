package cn.sky999.util;

import cn.sky999.service.impl.ConfigService;

public class ConfigUtil {

	public static String getConfigValueById(String configId) {
		ConfigService config =(ConfigService) SpringUtils.getBean("configService");
		return config.getById(configId).getConfigValue();
	}
}
