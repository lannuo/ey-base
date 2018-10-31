package cn.sky999.service;

import cn.sky999.baseEntity.CodeType;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;

public interface CodeTypeServiceIntFc {

	/**
	 * 保存代码类型
	 * 
	 * @param codeType
	 * @throws Exception
	 * @throws Exception 
	 */
	void saveEntity(CodeType codeType) throws Exception;

	/**
	 * 修改代码类型
	 * 
	 * @param codeType
	 * @throws Exception
	 * @throws Exception 
	 */
	void updateEntity(CodeType codeType) throws Exception;

	/**
	 * 删除代码类型<br>
	 * 删除代码信息，更新缓存
	 * 
	 * @param id
	 */
	void deleteEntity(String id);

	/**
	 * 根据ID获取代码类型
	 * 
	 * @param id
	 * @return
	 */
	CodeType getById(String id);

	/**
	 * 查询代码类型列表<br>
	 * 条件：类型ID,类型名称
	 * 
	 * @param page
	 * @param codeType
	 * @return
	 */
	PageData findList(Page page, CodeType codeType);

	/**
	 * 根据主键id删除
	 * @param ids
	 */
	void deleteByIds(String ids) throws Exception;
}
