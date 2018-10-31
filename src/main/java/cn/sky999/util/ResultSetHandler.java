package cn.sky999.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sky999.common.reflect.ReflectHelper;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

/**
 * ResultSet操作类-mybatis
 * 
 * @author Firevery
 */
public class ResultSetHandler {

	private Map<String, ResultMapping> columnMappings;
	private Class<?> type;

	public ResultSetHandler(MappedStatement ms) {
		ResultMap rsm = ms.getResultMaps().get(0);
		List<ResultMapping> propertyResultMappings = rsm.getPropertyResultMappings();
		columnMappings = new HashMap<>();
		for (ResultMapping rm : propertyResultMappings) {
			columnMappings.put(rm.getColumn(), rm);
		}
		// resultType="cn.fire.entity.Menu"实质会new一个resultMap
		type = rsm.getType();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> handleResultSet(ResultSet rs) throws SQLException, InstantiationException,
			IllegalAccessException, SecurityException, NoSuchFieldException, IllegalArgumentException {
		ResultSetMetaData metaData = rs.getMetaData();
		int colCount = metaData.getColumnCount();// 获取当前结果集的列数
		List list = new ArrayList<>();
		if (type == HashMap.class) {
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String colName = metaData.getColumnLabel(i);
					map.put(colName, rs.getObject(colName));
				}
				list.add(map);
			}
		} else {
			while (rs.next()) {
				Object obj = type.newInstance();
				for (int i = 1; i <= colCount; i++) {
					String colName = metaData.getColumnLabel(i);
					if(columnMappings.isEmpty()){
						ReflectHelper.setValueByFieldName(obj, colName, rs.getObject(colName));
					}
					if (columnMappings.containsKey(colName)) {
						String property = columnMappings.get(colName).getProperty();
						ReflectHelper.setValueByFieldName(obj, property, rs.getObject(colName));
					}
				}
				list.add(obj);
			}
		}
		return list;
	}
}
