package cn.sky999.service;


import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;

public interface IntefaceManagementServiceIntFc {
	
	/**
	 * 新增接口管理
	 */
	void saveEntity(String ifManagementName, String ifManagementLink, String ifManagementWay) throws Exception;

	/**
	 * 修改接口管理
	 */
	void updateEntity(String ifManagementName, String ifManagementLink, String ifManagementWay, String id) throws Exception;

	/**
	 * 删除接口管理
	 */
	void deleteEntity(String id) throws Exception;
	
	/**
	 * 查询接口管理
	 * @param page
	 * @param ifManagementName
	 * @return
	 * @throws Exception
	 */
	PageData seleteEntity(Page page, String ifManagementName) throws Exception;
}
