package cn.sky999.service.impl;

import cn.sky999.common.HQuery;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.common.procedure.Parameter;
import cn.sky999.dao.BaseDaoIntFc;
import cn.sky999.service.BaseServiceIntFc;
import cn.sky999.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Service("baseService")
@Transactional
public class BaseService implements BaseServiceIntFc {
	@Autowired
	private BaseDaoIntFc baseDao;

	protected String mapper;

	@Override
	public <T> T get(Class entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public <T> T save(Object entity) throws Exception {
		return (T)baseDao.save(entity);
	}

	@Override
	public void update(Object entity) throws Exception {
		baseDao.update(entity);
	}

	@Override
	public void saveOrUpdate(Object entity) throws Exception {
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Object entity) {
		baseDao.delete(entity);
	}

	@Override
	public void delete(Class entityClass, Serializable id) throws DataAccessException {
		baseDao.delete(entityClass, id);
	}

	@Override
	public Integer execute(String hql, Object... values) {
		return baseDao.execute(hql, values);
	}

	@Override
	public Integer execute(String hql, Map map) {
		return baseDao.execute(hql, map);
	}

	@Override
	public List find(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public List find(String hql, Object... values) {
		return baseDao.find(hql, values);
	}

	@Override
	public List find(String hql, Map map) {
		return baseDao.find(hql, map);
	}

	@Override
	public <T> T findOne(String hql) {
		return baseDao.findOne(hql);
	}

	@Override
	public <T> T findOne(String hql, Object... values) {
		return baseDao.findOne(hql, values);
	}

	@Override
	public <T> T findOne(String hql, Map map) {
		return baseDao.findOne(hql, map);
	}

	@Override
	public Map executeProc(String callSql, List<Parameter> paramList) {
		return baseDao.executeProc(callSql, paramList);
	}

	@Override
	public <T> T findForObject(String str, Object obj) {
		return baseDao.findForObject(mapper + "." + str, obj);
	}

	@Override
	public <T> List<T> findForList(String str, Object obj) {
		return baseDao.findForList(mapper + "." + str, obj);
	}

	@Override
	public PageData findPage(String str, Object obj, Page page) {
		return baseDao.findPage(mapper + "." + str, obj, page);
	}

	@Override
	public void flush() {
		baseDao.flush();
	}

	@Override
	public void evict(Object obj) {
		baseDao.evict(obj);
	}

	@Override
	public void clear() {
		baseDao.clear();
	}

	@Override
	public void merge(Object obj) {
		baseDao.merge(obj);
	}

	@Override
	public Object saveBySql(String str, Object obj) {
		return baseDao.saveBySql(mapper + "." + str, obj);
	}

	@Override
	public Object updateBySql(String str, Object obj) {
		return baseDao.updateBySql(mapper + "." + str, obj);
	}

	@Override
	public Object deleteBySql(String str, Object obj) {
		return baseDao.deleteBySql(mapper + "." + str, obj);
	}

	@Override
	public Integer getId(Class entityClass, Integer start) {
		return baseDao.getId(entityClass,start);
	}

	@Override
	public String getId(String head, Integer digit, String compId) throws Exception{
		return RedisUtil.getId(head,digit,compId);
	}

	/**
	 * 根据一个查询语句查询记录
	 *
	 * @param _query
	 */
	@Override
	public List find(HQuery _query) throws Exception {
		return baseDao.find(_query);
	}

	@Override
	public PageData find(HQuery _query, Page page) throws Exception {
		return baseDao.find(_query,page);
	}

	@Override
	public List<Map> findSqlMap(String sql, Map<String, Object> map) {
		return baseDao.findSqlMap(sql,map);
	}

	/**
	 * 带Map参数sql,返回指定对象
	 *
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	@Override
	public List findSqlMap(String sql, Map<String, Object> map, Class clazz) {
		return baseDao.findSqlMap(sql,map,clazz);
	}

	/**
	 * 带Map参数sql和分页,返回指定对象,
	 *
	 * @param sql
	 * @param map
	 * @param clazz
	 * @param page
	 * @return
	 */
	@Override
	public PageData findSqlMap(String sql, Map<String, Object> map, Class clazz, Page page) {
		return baseDao.findSqlMap(sql,map,clazz,page);
	}

	/**
	 * 返回非托管实体
	 *
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	@Override
	public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz) {
		return baseDao.findSqlMapRVo(sql,map,clazz);
	}

	/**
	 * 非托管实体分页,返回指定对象,
	 *
	 * @param sql
	 * @param map
	 * @param clazz
	 * @param page
	 * @return
	 */
	@Override
	public PageData findSqlMapRVo(String sql, Map<String, Object> map, Class clazz, Page page) {
		return baseDao.findSqlMapRVo(sql,map,clazz,page);
	}
}
