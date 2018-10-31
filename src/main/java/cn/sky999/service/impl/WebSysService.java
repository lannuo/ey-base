package cn.sky999.service.impl;

import cn.sky999.baseEntity.WebSys;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.service.WebSysServiceIntFc;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("webSysService")
public class WebSysService extends BService implements WebSysServiceIntFc {

	public WebSysService() {
		this.mapper = "WebSysMapper";
	}

	@Override
	public void saveEntity(WebSys webSys) throws Exception {
		if (isExistSysCode(webSys.getSysCode())) {
			throw new Exception("参数ID已存在");
		}
		save(webSys);
	}

	@Override
	@CacheEvict(value="redis1",key="'webSys_*'")
	public void updateEntity(WebSys webSys) throws Exception {
		update(webSys);
	}

	@Override
	@CacheEvict(value="redis1",key="'webSys_'+#id")
	public void deleteEntity(String id) {
		delete(WebSys.class, id);
	}

	@Override
	@Cacheable(value="redis1",key="'webSys_'+#id")
	public WebSys getById(String id) {
		return get(WebSys.class, id);
	}

	@Override
	public PageData findList(Page page, WebSys webSys) {
		return findPage("findList", webSys, page);
	}

	public Boolean isExistSysCode(String sysCode) {
		if (this.find("from WebSys where sysCode=?", sysCode).size() > 0) {
			return true;
		}
		return false;
	}
	@CacheEvict(value="redis1",key="'webSys_*'")
	public void deleteByIds(String ids) throws Exception{
		String[] strIds = StringUtils.split(ids,";");
		for(String id:strIds){
			this.deleteEntity(id);
		}
	}
}
