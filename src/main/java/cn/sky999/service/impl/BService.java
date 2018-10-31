package cn.sky999.service.impl;

import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.common.procedure.Parameter;
import cn.sky999.dao.DaoIntFc;
import cn.sky999.service.BServiceIntFc;
import cn.sky999.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service("bService")
@Transactional
public class BService implements BServiceIntFc {
	@Autowired
	private DaoIntFc dao;

	protected String mapper;

	@Override
	public <T> T get(Class entityClass, Serializable id) {
		return dao.get(entityClass, id);
	}

	@Override
	public <T> T save(Object entity) throws Exception {
		return (T)dao.save(entity);
	}

	@Override
	public void update(Object entity) throws Exception {
		dao.update(entity);
	}

	@Override
	public void saveOrUpdate(Object entity) throws Exception {
		dao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Object entity) {
		dao.delete(entity);
	}

	@Override
	public void delete(Class entityClass, Serializable id) throws DataAccessException {
		dao.delete(entityClass, id);
	}

	@Override
	public Integer execute(String hql, Object... values) {
		return dao.execute(hql, values);
	}

	@Override
	public Integer execute(String hql, Map map) {
		return dao.execute(hql, map);
	}

	@Override
	public List find(String hql) {
		return dao.find(hql);
	}

	@Override
	public List find(String hql, Object... values) {
		return dao.find(hql, values);
	}

	@Override
	public List find(String hql, Map map) {
		return dao.find(hql, map);
	}

	@Override
	public <T> T findOne(String hql) {
		return dao.findOne(hql);
	}

	@Override
	public <T> T findOne(String hql, Object... values) {
		return dao.findOne(hql, values);
	}

	@Override
	public <T> T findOne(String hql, Map map) {
		return dao.findOne(hql, map);
	}

	@Override
	public Map executeProc(String callSql, List<Parameter> paramList) {
		return dao.executeProc(callSql, paramList);
	}

	@Override
	public <T> T findForObject(String str, Object obj) {
		return dao.findForObject(mapper + "." + str, obj);
	}

	@Override
	public <T> List<T> findForList(String str, Object obj) {
		return dao.findForList(mapper + "." + str, obj);
	}

	@Override
	public PageData findPage(String str, Object obj, Page page) {
		return dao.findPage(mapper + "." + str, obj, page);
	}

	@Override
	public void flush() {
		dao.flush();
	}

	@Override
	public void evict(Object obj) {
		dao.evict(obj);
	}

	@Override
	public void clear() {
		dao.clear();
	}

	@Override
	public void merge(Object obj) {
		dao.merge(obj);
	}

	@Override
	public Object saveBySql(String str, Object obj) {
		return dao.saveBySql(mapper + "." + str, obj);
	}

	@Override
	public Object updateBySql(String str, Object obj) {
		return dao.updateBySql(mapper + "." + str, obj);
	}

	@Override
	public Object deleteBySql(String str, Object obj) {
		return dao.deleteBySql(mapper + "." + str, obj);
	}

	@Override
	public Integer getId(Class entityClass, Integer start) {
		return dao.getId(entityClass,start);
	}

	@Override
	public String getId(String head, Integer digit, String compId) throws Exception{
		return RedisUtil.getId(head,digit,compId);
	}
}
