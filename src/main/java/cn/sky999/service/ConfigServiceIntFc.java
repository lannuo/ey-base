package cn.sky999.service;

import cn.sky999.baseEntity.Config;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;

public interface ConfigServiceIntFc {

	/**
	 * 保存参数配置<br>
	 * 修改redis缓存
	 * 
	 * @param config
	 * @throws Exception
	 * @throws Exception 
	 */
	void saveEntity(Config config) throws Exception;

	/**
	 * 修改参数配置<br>
	 * 修改redis缓存
	 * 
	 * @param config
	 * @throws Exception
	 */
	void updateEntity(Config config) throws Exception;

	/**
	 * 删除参数配置<br>
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
	Config getById(String id);

	/**
	 * 查询参数配置列表<br>
	 * 条件：参数ID，参数名称
	 * 
	 * @param page
	 * @param config
	 * @return
	 */
	PageData findList(Page page, Config config);

	/**
	 * 根据ids批量删除
	 * @param ids
	 * @throws Exception
	 */
	void deleteByIds(String ids) throws Exception;
}
