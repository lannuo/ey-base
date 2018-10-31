package cn.sky999.dao;

import cn.sky999.common.HQuery;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.common.procedure.Parameter;
import org.springframework.dao.DataAccessException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoIntFc {
	/**
	 * 获取持久化实例
	 *
	 * @param entityClass
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	<T> T get(Class entityClass, Serializable id);

	/**
	 * 保存实例
	 *
	 * @param entity
	 */
	<T> T save(Object entity)throws Exception;

	/**
	 * 更新实例
	 *
	 * @param entity
	 */
	void update(Object entity)throws Exception;

	/**
	 * 保存或者更新实例
	 *
	 * @param entity
	 */
	void saveOrUpdate(Object entity)throws Exception;

	/**
	 * 删除实例
	 *
	 * @param entity
	 */
	void delete(Object entity);

	/**
	 * 根据ID删除实例
	 *
	 * @param entityClass
	 * @param id
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	void delete(Class entityClass, Serializable id) throws DataAccessException;

	/**
	 * 执行HQL
	 *
	 * @param hql
	 * @param values
	 * @return
	 */
	Integer execute(String hql, Object... values);

	/**
	 * 执行HQL
	 *
	 * @param hql
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Integer execute(String hql, Map map);

	/**
	 * HQL查询
	 *
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List find(String hql);

	/**
	 * HQL查询
	 *
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List find(String hql, Object... values);

	/**
	 * HQL查询
	 *
	 * @param hql
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List find(String hql, Map map);

	/**
	 * HQL查询对象
	 *
	 * @param hql
	 * @return
	 */
	<T> T findOne(String hql);

	/**
	 * HQL查询对象
	 *
	 * @param hql
	 * @param values
	 * @return
	 */
	<T> T findOne(String hql, Object... values);

	/**
	 * HQL查询对象
	 *
	 * @param hql
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T> T findOne(String hql, Map map);

	/**
	 * 执行存储过程<br>
	 * callSql例：?=call proc(?,?,?) || call proc(?,?) <br>
	 * paramList按?顺序排列<br>
	 * Map返回：returnValues-返回值/输出参数值，returnLists-返回结果集集合
	 *
	 * @param callSql
	 * @param paramList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map executeProc(String callSql, List<Parameter> paramList);

	/**
	 * Mybatis查询对象
	 *
	 * @param str
	 * @param obj
	 * @return
	 */
	<T> T findForObject(String str, Object obj);

	/**
	 * Mybatis查询List
	 *
	 * @param str
	 * @param obj
	 * @return
	 */
	<T> List<T> findForList(String str, Object obj);

	/**
	 * mybatis分页查询
	 *
	 * @param str
	 * @param obj
	 * @param page
	 * @return
	 */
	PageData findPage(String str, Object obj, Page page);

	/**
	 * 强制进行从内存到数据库的同步
	 */
	void flush();

	/**
	 * 清除指定的缓冲对象
	 *
	 * @param obj
	 */
	void evict(Object obj);

	/**
	 * 强制清除Session缓存
	 */
	void clear();

	/**
	 * 将当前对象信息保存到数据库,并且不会将对象转换成持久化状态
	 *
	 * @param obj
	 */
	void merge(Object obj);

	/**
	 * 保存对象
	 *
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object saveBySql(String str, Object obj);

	/**
	 * 修改对象
	 *
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object updateBySql(String str, Object obj);

	/**
	 * 删除对象
	 *
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object deleteBySql(String str, Object obj);
	/**
	 * 递增获取id 10001、10002...
	 * @param entityClass
	 * @param start 10001
	 * @return
	 */
	Integer getId(Class entityClass, Integer start);

	/**
	 * 根据一个查询语句查询记录
	 */
	public List find(HQuery _query) throws Exception;

	public PageData find(HQuery _query,Page page) throws Exception;

	public List<Map> findSqlMap(String sql,Map<String,Object> map);
	/**
	 * 带Map参数sql,返回指定对象
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	public List findSqlMap(String sql,Map<String,Object> map,Class clazz);
	/**
	 * 带Map参数sql和分页,返回指定对象,
	 * @param sql
	 * @param map
	 * @param page
	 * @return
	 */
	public PageData findSqlMap(String sql,Map<String,Object> map,Class clazz,Page page);
	/**
	 * 返回非托管实体
	 * @param sql
	 * @param map
	 * @param clazz
	 * @return
	 */
	public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz);

	/**
	 * 非托管实体分页,返回指定对象,
	 * @param sql
	 * @param map
	 * @param page
	 * @return
	 */
	public PageData findSqlMapRVo(String sql,Map<String,Object> map,Class clazz,Page page);
}
