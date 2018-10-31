package cn.sky999.service;

import java.util.List;
import java.util.Map;

import cn.sky999.baseEntity.CodeInfo;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;

public interface CodeInfoServiceIntFc {

	/**
	 * 保存代码信息<br>
	 * 更新缓存
	 * 
	 * @param codeInfo
	 * @throws Exception
	 * @throws Exception
	 */
	void saveEntity(CodeInfo codeInfo) throws Exception;

	/**
	 * 修改代码信息<br>
	 * 更新缓存
	 * 
	 * @param codeInfo
	 * @throws Exception
	 * @throws Exception
	 */
	void updateEntity(CodeInfo codeInfo) throws Exception;

	/**
	 * 删除代码信息<br>
	 * 更新缓存
	 * 
	 * @param id
	 */
	void deleteEntity(String id);

	/**
	 * 根据ID获取代码信息
	 * 
	 * @param id
	 * @return
	 */
	CodeInfo getById(String id);

	/**
	 * 查询代码信息列表<br>
	 * 
	 * @param page
	 * @param typeId
	 * @return
	 */
	PageData findList(Page page, String typeId);

	/**
	 * 获取codeInfo集合数据
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findList();

	CodeInfo getInfoContent(String typeId, String infoValue);

	/**
	 * 查询代码信息下拉列表<br>
	 * 
	 * @param typeId
	 * @return
	 */
	List<Map<String, Object>> findSelectList(String typeId);

	/**
	 * 获取该类型下的所所有内容
	 * 
	 * @param codeType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List getCodeInfoList(String codeType);

	/**
	 * 根据ids批量删除
	 * @param ids
	 * @throws Exception
	 */
	void deleteByIds(String ids) throws Exception;
}
