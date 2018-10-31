package cn.sky999.service.impl;

import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import cn.sky999.baseEntity.CodeType;
import cn.sky999.service.CodeTypeServiceIntFc;

@Service("codeTypeService")
public class CodeTypeService extends BService implements CodeTypeServiceIntFc {

	public CodeTypeService() {
		this.mapper = "CodeTypeMapper";
	}

	@Override
	public void saveEntity(CodeType codeType) throws Exception {
		if (isExistCodeTypeId(codeType.getTypeId())) {
			throw new Exception("ID已存在");
		}
		save(codeType);
	}

	@Override
	public void updateEntity(CodeType codeType) throws Exception {
		update(codeType);
	}

	@Override
	public void deleteEntity(String id) {
		delete(CodeType.class, id);
		this.execute("delete from CodeInfo where typeId=?", id);
	}

	@Override
	public CodeType getById(String id) {
		return get(CodeType.class, id);
	}

	@Override
	public PageData findList(Page page, CodeType codeType) {
		return findPage("findCodeTypeList", codeType, page);
	}

	public Boolean isExistCodeTypeId(String codeTypeId) {
		if (this.find("from CodeType where typeId=?", codeTypeId).size() > 0) {
			return true;
		}
		return false;
	}
    @CacheEvict(value="redis1",key="'codeInfo_*'")
    public void deleteByIds(String ids) throws Exception{
	    String[] strIds= StringUtils.split(ids,";");
	    for(String id:strIds){
	        deleteEntity(id);
        }
    }
}
