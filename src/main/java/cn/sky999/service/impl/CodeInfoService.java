package cn.sky999.service.impl;

import cn.sky999.baseEntity.CodeInfo;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.common.uuid.UUIDUtil;
import cn.sky999.service.CodeInfoServiceIntFc;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("codeInfoService")
public class CodeInfoService extends BService implements CodeInfoServiceIntFc {

	public CodeInfoService() {
		this.mapper = "CodeInfoMapper";
	}

	@Override
	@CacheEvict(value="redis1",key="'codeInfo_*'")
	public void saveEntity(CodeInfo codeInfo) throws Exception {
		codeInfo.setInfoId(UUIDUtil.get32UUID());
		save(codeInfo);
	}

	@Override
	@CacheEvict(value="redis1",key="'codeInfo_*'")
	public void updateEntity(CodeInfo codeInfo) throws Exception {
		update(codeInfo);
	}

	@Override
	public void deleteEntity(String id) {
		delete(CodeInfo.class, id);
	}

	@Override
	@Cacheable(value="redis1",key="'codeInfo_'+#id")
	public CodeInfo getById(String id) {
		return get(CodeInfo.class, id);
	}

	@Override
	public PageData findList(Page page, String typeId) {
		return findPage("findCodeInfoList", typeId, page);
	}

	@Override
	public List<CodeInfo> findList() {
		return this.findForList("findCodeInfoAandCodeTypeList", null);
	}

	@Override
	@Cacheable(value="redis1",key="'codeInfo_' + #typeId + #infoValue")
	public CodeInfo getInfoContent(String typeId, String infoValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		map.put("infoValue", infoValue);
		List<CodeInfo> list = this.findForList("getInfoContent", map);
		return list.size() == 1 ? list.get(0) : null;
	}

	@Override
	@Cacheable(value="redis1",key="'codeInfo_select' + #typeId")
	public List<Map<String, Object>> findSelectList(String typeId) {
		List<CodeInfo> ls = this.find(" from CodeInfo where typeId = ? order by sortNo asc", typeId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (ls != null && ls.size() > 0) {
			for (CodeInfo cf : ls) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", cf.getInfoContent());
				m.put("value", cf.getInfoValue());
				list.add(m);
			}
		}
		return list;
	}

	@Override
	@Cacheable(value="redis1",key="'codeInfo_' + #typeId")
	public List getCodeInfoList(String typeId) {
		return this.find("from CodeInfo where typeId=? order by sortNo asc", typeId);
	}

	@CacheEvict(value="redis1",key="'codeInfo_*'")
	public void deleteByIds(String ids) throws Exception{
		String[] strIds = StringUtils.split(ids,";");
		for(String id:strIds){
			this.deleteEntity(id);
		}
	}

	@Cacheable(value="redis1",key="'code'+#type+#value")
	public CodeInfo findByTV(String type,String value) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("value", value);
		List<CodeInfo> ls = this.find(" from CodeInfo where typeId=:type and infoValue=:value",map);
		if(ls!=null&&ls.size()>0)
			return ls.get(0);
		return null;
	}
}
