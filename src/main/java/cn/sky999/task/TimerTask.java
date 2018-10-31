package cn.sky999.task;

import org.springframework.beans.factory.annotation.Autowired;

import cn.sky999.service.LogServiceIntFc;

/**
 * @author Firevery
 * @date 创建时间：2017年3月23日
 * @version 1.0
 */
public class TimerTask {
	@Autowired
    LogServiceIntFc logServiceIntFc;

	/**
	 * 每天晚上12点清理系统日志（保留一个月）
	 */
	public void clearSysLog() {
		logServiceIntFc.deleteKeepOneMonth();
	}

}
