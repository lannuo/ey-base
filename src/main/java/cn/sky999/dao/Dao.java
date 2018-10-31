package cn.sky999.dao;

import cn.sky999.baseEntity.BaseModel;
import cn.sky999.common.HQuery;
import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.common.procedure.Parameter;
import cn.sky999.util.PageUtil;
import cn.sky999.util.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository("dao")
public class Dao implements DaoIntFc {

	@Autowired
	private SessionFactory baseSessionFactory;

	@Resource(name = "baseSqlSessionTemplate")
	private SqlSessionTemplate baseSqlSessionTemplate;

	private Session getSession() {
		return baseSessionFactory.getCurrentSession();
	}

	private void close(Statement cs, ResultSet rs) {
		try {
			if (cs != null) {
				cs.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T get(Class entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}

	public  <T> T save(Object entity) throws Exception {
		if(entity instanceof BaseModel) {
			BaseModel o = (BaseModel) entity;
			o.setCreateTime(new Date());
			o.setDeleteFlag(1);
			return (T)getSession().save(o);
		}else{
			return (T)getSession().save(entity);
		}
	}

	public void update(Object entity) throws Exception {
		if(entity instanceof BaseModel) {
			BaseModel o = (BaseModel) entity;
			o.setUpdateTime(new Date());
			getSession().update(o);
		}else {
			getSession().update(entity);
		}
	}

	public void saveOrUpdate(Object entity) throws Exception {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}

	@SuppressWarnings("rawtypes")
	public void delete(Class entityClass, Serializable id) throws DataAccessException {
		getSession().delete(get(entityClass, id));
	}

	public Integer execute(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; ++i) {
				query.setParameter(i, values[i]);
			}
		}
		return Integer.valueOf(query.executeUpdate());
	}

	@SuppressWarnings("rawtypes")
	public Integer execute(String hql, Map map) {
		Query query = getSession().createQuery(hql);
		if (map != null) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				query.setParameter(key, map.get(key));
			}
		}
		return Integer.valueOf(query.executeUpdate());
	}

	@SuppressWarnings("rawtypes")
	public List find(String hql) {
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("rawtypes")
	public List find(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; ++i) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	public List find(String hql, Map map) {
		Query query = getSession().createQuery(hql);
		if (map != null) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				query.setParameter(key, map.get(key));
			}
		}
		return query.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T findOne(String hql) {
		Query query = getSession().createQuery(hql);
		List list = query.list();
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		}
		return null;
		// return (T) getSession().createQuery(hql).uniqueResult();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T findOne(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; ++i) {
				query.setParameter(i, values[i]);
			}
		}
		List list = query.list();
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		}
		return null;
		// return (T) query.uniqueResult();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T findOne(String hql, Map map) {
		Query query = getSession().createQuery(hql);
		if (map != null) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				query.setParameter(key, map.get(key));
			}
		}
		List list = query.list();
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		}
		return null;
		// return (T) query.uniqueResult();
	}

	@SuppressWarnings("rawtypes")
	public Map executeProc(final String callSql, final List<Parameter> paramList) {
		final Map map = new HashMap();
		getSession().doWork(new Work() {
			@SuppressWarnings("unchecked")
			public void execute(Connection conn) throws SQLException {
				CallableStatement cs = null;
				ResultSet rs = null;
				cs = conn.prepareCall(callSql);
				List<Integer> outList = new ArrayList<Integer>();
				if (paramList!=null&&paramList.size()>0) {
					for (int i = 0; i < paramList.size(); i++) {
						Parameter p = paramList.get(i);
						if (p.getParamValue() != null) {
							cs.setObject(i + 1, p.getParamValue());// 设置参数值
						}
						if (p.isOut()) {
							outList.add(i + 1);
							cs.registerOutParameter(i + 1, p.getParamType());// 设置返回参数类型
						}
					}
				}
				boolean hasResult = cs.execute();
				if (outList.size() > 0) {
					List<Object> list = new ArrayList<Object>();
					for (Integer idx : outList) {
						String obj = cs.getString(idx);
						list.add(obj);// 获取返回值
					}
					map.put("returnValues", list);
				}

				List<List<Map<String, Object>>> rsLists = null;
				ResultSetMetaData metaData = null;
				while (hasResult) {// 遍历结果集
					rsLists = new ArrayList<List<Map<String, Object>>>();
					List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();// 用于装该结果集的内容
					rs = cs.getResultSet();// 获取当前结果集
					metaData = rs.getMetaData();
					int colCount = metaData.getColumnCount();// 获取当前结果集的列数
					while (rs.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 1; i <= colCount; i++) {
							String colName = metaData.getColumnName(i);
							map.put(colName, rs.getObject(colName));
						}
						rsList.add(map);
					}
					rsLists.add(rsList);
					close(null, rs);// 遍历完一个结果集，将其关闭
					hasResult = cs.getMoreResults();// 移到下一个结果集
				}
				if (rsLists != null) {
					map.put("returnLists", rsLists);
				}
				close(cs, rs);
			}
		});
		return map;
	}

	public <T> T findForObject(String str, Object obj) {
		return baseSqlSessionTemplate.selectOne(str, obj);
	}

	public <T> List<T> findForList(String str, Object obj) {
		return baseSqlSessionTemplate.selectList(str, obj);
	}

	public PageData findPage(String str, Object obj, Page page) {
		SqlSessionFactory factory = baseSqlSessionTemplate.getSqlSessionFactory();
		final MappedStatement ms = factory.getConfiguration().getMappedStatement(str);

		final Object param = obj;
		final BoundSql boundSql = ms.getBoundSql(param);

		final PageData data = new PageData();

		final String countSql = PageUtil.generateCountSql(boundSql.getSql());
		final BoundSql countBS = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), param);

		getSession().doWork(new Work() {
			public void execute(Connection conn) throws SQLException {
				PreparedStatement st = conn.prepareStatement(countSql);
				PageUtil.setParameters(st, ms, countBS, param);
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					data.setTotal(rs.getInt(1));
				}
				rs.close();
				st.close();
			}
		});
		final String pageSql = PageUtil.generatePageSql(boundSql.getSql(), page);
		final BoundSql pageBS = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), param);

		getSession().doWork(new Work() {
			public void execute(Connection conn) throws SQLException {
				PreparedStatement st = conn.prepareStatement(pageSql);
				PageUtil.setParameters(st, ms, pageBS, param);
				ResultSet rs = st.executeQuery();
				ResultSetHandler rsh = new ResultSetHandler(ms);
				try {
					data.setRows(rsh.handleResultSet(rs));
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				rs.close();
				st.close();
			}
		});
		return data;
	}

	public void flush() {
		getSession().flush();
	}

	public void evict(Object obj) {
		getSession().evict(obj);
	}

	public void clear() {
		getSession().clear();
	}

	public void merge(Object obj) {
		getSession().merge(obj);
	}

	public Object saveBySql(String str, Object obj) {
		return baseSqlSessionTemplate.insert(str, obj);
	}

	public Object updateBySql(String str, Object obj) {
		return baseSqlSessionTemplate.update(str, obj);
	}

	public Object deleteBySql(String str, Object obj) {
		return baseSqlSessionTemplate.delete(str, obj);
	}

	public int update(String sql) {
		Query query = baseSessionFactory.getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	/**
	 * 递增获取id 10001、10002...
	 * @param entityClass
	 * @param start 10001
	 * @return
	 */
	@Override
	public Integer getId(Class entityClass,Integer start){
		String hql="select max(id+0) from "+entityClass.getSimpleName();
		List<Long> results=this.find(hql);
		return results!=null&&results.size()>0?(results.get(0)!=null?results.get(0).intValue()+1:start):start;
	}
	@Override
	public List find(HQuery _query) throws Exception {
		List itr = null;
		try {
			StringBuffer query_str = new StringBuffer(_query.getQueryString());
			Query query = baseSessionFactory.getCurrentSession().createQuery(query_str.toString());
			if(_query.getPara() != null){
				Iterator iter = _query.getPara().entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					query.setString(entry.getKey().toString(), entry.getValue().toString());
				}
			}
			itr = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return itr == null || itr.size() == 0 ? null : itr;
	}

	@Override
	public PageData find(HQuery _query, Page page) throws Exception {
		List itr = null;
		PageData pd=new PageData();
		pd.setTotal(this.getAllCountHquery(_query));
		try {
			StringBuffer query_str = new StringBuffer(_query.getQueryString());
			Query query = baseSessionFactory.getCurrentSession().createQuery(query_str.toString());
			if(_query.getPara() != null){
				Iterator iter = _query.getPara().entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					query.setString(entry.getKey().toString(), entry.getValue().toString());
				}
			}
			int pageno = page.getPage();
			query.setFirstResult((pageno - 1) * page.getRows());
			query.setMaxResults(page.getRows());
			itr = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		pd.setRows(itr);
		return pd;
	}

	@Override
	public List<Map> findSqlMap(String sql, Map<String, Object> map) {
		Query query = baseSessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setProperties(map);
		return query.list();
	}

	@Override
	public List findSqlMap(String sql, Map<String, Object> map, Class clazz) {
		Query query = baseSessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
		return query.list();
	}

	@Override
	public PageData findSqlMap(String sql, Map<String, Object> map, Class clazz, Page page) {
		PageData pd = new PageData();
		Query query = baseSessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(clazz).setProperties(map);
		query.setFirstResult((page.getPage() - 1) * page.getRows());
		query.setMaxResults(page.getRows());
		List<Map> ls = query.list();
		if (ls != null && ls.size() > 0) {
			sql = "SELECT count(1) FROM (" + sql + ")  ffffffffff";
			Query query2 = baseSessionFactory.getCurrentSession().createSQLQuery(sql).setProperties(map);
			Number summoney = (Number) query2.uniqueResult();
			int count = summoney.intValue();
			pd.setTotal(count);
			pd.setRows(ls);
		}
		return pd;
	}

	@Override
	public List findSqlMapRVo(String sql, Map<String, Object> map, Class clazz) {
		Query query = baseSessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		return query.list();
	}

	@Override
	public PageData findSqlMapRVo(String sql, Map<String, Object> map, Class clazz, Page page) {
		PageData pd = new PageData();
		Query query = baseSessionFactory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz)).setProperties(map);
		query.setFirstResult((page.getPage() - 1) * page.getRows());
		query.setMaxResults( page.getRows());
		List<Map> ls = query.list();
		if (ls != null && ls.size() > 0) {
			sql = "SELECT count(1) FROM (" + sql + ")  ffffffffff";
			Query query2 = baseSessionFactory.getCurrentSession().createSQLQuery(sql).setProperties(map);
			Number summoney = (Number) query2.uniqueResult();
			int count = summoney.intValue();
			pd.setTotal(count);
			pd.setRows(ls);
		}
		return pd;
	}

	public int getAllCountHquery(HQuery _query) throws Exception {
		try {
			String query_str = _query.getQueryString();
			if (query_str.startsWith("select") || query_str.startsWith("SELECT"))
				query_str = "select count(*) "+ query_str.substring(query_str.indexOf("from"));
			else
				query_str = "select count(*) " + query_str;
			Query query = baseSessionFactory.getCurrentSession().createQuery(query_str);
			if(_query.getPara() != null){
				Iterator iter = _query.getPara().entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					query.setString(entry.getKey().toString(), entry.getValue().toString());
				}
			}
			int count = 0;
			Number summoney = (Number) (query.iterate().next());
			count = summoney.intValue();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
