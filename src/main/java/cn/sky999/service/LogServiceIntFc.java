package cn.sky999.service;

import java.util.Date;

import cn.sky999.baseEntity.Log;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;

public interface LogServiceIntFc {

	/**
	 * 保存日志
	 * 
	 * @param log
	 * @throws Exception 
	 */
	void saveEntity(Log log) throws Exception;

	/**
	 * 删除日志
	 * 
	 * @param id
	 */
	void deleteEntity(String id);

	/**
	 * 根据ID获取日志
	 * 
	 * @param id
	 * @return
	 */
	Log getById(String id);

	/**
	 * 查询日志列表<br>
	 * 条件：开始时间，结束时间，状态
	 * 
	 * @param page
	 * @param filter
	 * @return
	 */
	PageData findList(Page page, Date startTime, Date endTime, String status, String filter);
	/**
	 * 清除日志保留一个月
	 */
	void deleteKeepOneMonth();

}
