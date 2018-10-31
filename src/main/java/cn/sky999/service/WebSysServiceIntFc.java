package cn.sky999.service;

import cn.sky999.baseEntity.WebSys;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;

public interface WebSysServiceIntFc {

	/**
	 * 修改redis缓存
	 * 
	 * @param webSys
	 * @throws Exception
	 * @throws Exception 
	 */
	void saveEntity(WebSys webSys) throws Exception;

	/**
	 * 修改redis缓存
	 * 
	 * @param webSys
	 * @throws Exception
	 */
	void updateEntity(WebSys webSys) throws Exception;

	/**
	 * 修改redis缓存
	 * 
	 * @param id
	 */
	void deleteEntity(String id);

	/**
	 * 根据ID获取参数配置
	 * 
	 * @param id
	 * @return
	 */
	WebSys getById(String id);

	/**
	 * 条件：参数ID，参数名称
	 * 
	 * @param page
	 * @param webSys
	 * @return
	 */
	PageData findList(Page page, WebSys webSys);

	/**
	 * 根据ids批量删除
	 * @param ids
	 * @throws Exception
	 */
	void deleteByIds(String ids) throws Exception;
}
