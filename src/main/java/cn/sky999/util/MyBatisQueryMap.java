package cn.sky999.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Firevery
 * @date 创建时间：2016年1月22日
 * @version 1.0
 */
public class MyBatisQueryMap extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -933985519456690697L;

	public Object setValue(String key, Object value) {
		return this.setValue(key, value, false);
	}

	public Object setValue(String key, Object value, boolean like) {
		if (value != null) {
			if (like && value instanceof String && !value.toString().equals("")) {
				return this.put(key, "%" + value + "%");
			}
			return this.put(key, value);
		}
		return null;
	}

	/**
	 * 
	 * @param obj
	 *            实体类对象
	 * @param set
	 *            HashSet，将要模糊匹配的字段放入
	 */
	public void setEntity(Object obj, Set<String> set) {
		setEntity(obj, obj.getClass(), set);
	}

	@SuppressWarnings("rawtypes")
	private void setEntity(Object obj, Class clazz, Set<String> set) {// 存在bug，暂不启用
		try {
			Field[] fields = clazz.getDeclaredFields();// 获得属性
			for (Field field : fields) {
				if (!field.getName().equals("serialVersionUID")) {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
					Method getMethod = pd.getReadMethod();// 获得get方法
					Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
					if (o != null) {
						if (set != null) {
							this.setValue(field.getName(), o, set.contains(field.getName()));
							set.remove(field.getName());
						} else {
							this.setValue(field.getName(), o, false);
						}
					}
				}
			}
			if (!(clazz.getSuperclass().getName().equals("java.lang.Object"))) {// 所有类都继承Object
				setEntity(obj, clazz.getSuperclass(), set);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
