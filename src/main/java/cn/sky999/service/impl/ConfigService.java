package cn.sky999.service.impl;

import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.sky999.baseEntity.Config;
import cn.sky999.service.ConfigServiceIntFc;

@Service("configService")
public class ConfigService extends BService implements ConfigServiceIntFc {

	public ConfigService() {
		this.mapper = "ConfigMapper";
	}

	@Override
	public void saveEntity(Config config) throws Exception {
		if (isExistConfigId(config.getConfigId())) {
			throw new Exception("参数ID已存在");
		}
		save(config);
	}

	@Override
	@CacheEvict(value="redis1",key="'config_*'")
	public void updateEntity(Config config) throws Exception {
		update(config);
	}

	@Override
	public void deleteEntity(String id) {
		delete(Config.class, id);
	}

	@Override
	@Cacheable(value="redis1",key="'config_'+#id")
	public Config getById(String id) {
		return get(Config.class, id);
	}

	@Override
	public PageData findList(Page page, Config config) {
		return findPage("findConfigList", config, page);
	}

	public Boolean isExistConfigId(String configId) {
		if (this.find("from Config where configId=?", configId).size() > 0) {
			return true;
		}
		return false;
	}
	@CacheEvict(value="redis1",key="'config_*'")
	public void deleteByIds(String ids) throws Exception{
		String[] strIds = StringUtils.split(ids,";");
		for(String id:strIds){
			this.deleteEntity(id);
		}
	}
}
