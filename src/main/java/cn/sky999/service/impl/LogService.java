package cn.sky999.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.sky999.common.date.DateUtil;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import org.springframework.stereotype.Service;

import cn.sky999.baseEntity.Log;
import cn.sky999.service.LogServiceIntFc;

@Service("logService")
public class LogService extends BService implements LogServiceIntFc {

	public LogService() {
		this.mapper = "LogMapper";
	}

	@Override
	public void saveEntity(Log log) throws Exception {
		save(log);
	}

	@Override
	public void deleteEntity(String id) {
		delete(Log.class, id);
	}

	@Override
	public Log getById(String id) {
		return get(Log.class, id);
	}

	@Override
	public PageData findList(Page page, Date startTime, Date endTime, String status, String filter) {
		Map<String, Object> log = new HashMap<String, Object>();
		log.put("startTime", startTime);
		log.put("endTime", endTime);
		log.put("status", status);
		log.put("filter", filter);
		return findPage("findLogList", log, page);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteKeepOneMonth() {
		String time= DateUtil.formatDateTime(DateUtil.addDay(new Date(), -30));
		Map map = new HashMap<String, String>();
		map.put("time", time);
		this.deleteBySql("deleteKeepOneMonth", map);
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.formatDateTime(DateUtil.addDay(new Date(), -30)));
	}
}
