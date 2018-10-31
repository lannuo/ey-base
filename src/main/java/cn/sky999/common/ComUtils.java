package cn.sky999.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class ComUtils {
	/**
	 * 
	 * @param <T>
	 * @param targetList
	 *            要排序的集合（使用泛型）
	 * @param sortField
	 *            要排序的集合中的实体类的某个字段
	 * @param sortMode
	 *            排序的方式（升序asc/降序desc）
	 */
	@SuppressWarnings("unchecked")
	public static <T> void sort(List<T> targetList, final String sortField, final String sortMode) {
		// 使用集合的sort方法 ，并且自定义一个排序的比较器
		/**
		 * API文档： public static <T> void sort(List<T> list,Comparator<? super T>
		 * c) 根据指定比较器产生的顺序对指定列表进行排序。此列表内的所有元素都必须可使用指定比较器 相互比较 （也就是说，对于列表中的任意 e1
		 * 和 e2 元素， c.compare(e1, e2) 不得抛出 ClassCastException）。 参数： list -
		 * 要排序的列表。 c - 确定列表顺序的比较器。 null 值指示应该使用元素的 自然顺序。
		 */
		Collections.sort(targetList, new Comparator() {
			// 匿名内部类，重写compare方法
			public int compare(Object obj1, Object obj2) {
				int result = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
					// 获取需要排序字段的“get方法名”
					String methodStr = "get" + newStr;
					/**
					 * API文档：： getMethod(String name, Class<?>...
					 * parameterTypes) 返回一个 Method 对象，它反映此 Class
					 * 对象所表示的类或接口的指定公共成员方法。
					 */
					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr, null);
					if (sortMode != null && "desc".equals(sortMode)) {
						/**
						 * Method类代表一个方法，invoke（调用）就是调用Method类代表的方法。
						 * 它可以让你实现动态调用，也可以动态的传入参数。
						 * API文档：（这个英文解释更地道易懂，原谅我是英文渣,哎！） invoke(Object obj,
						 * Object... args) Invokes the underlying method
						 * represented by this Method object, on the specified
						 * object with the specified parameters.
						 */
						/**
						 * API文档： String-----public int compareTo(String
						 * anotherString) 按字典顺序比较两个字符串。该比较基于字符串中各个字符的 Unicode 值。
						 * 按字典顺序将此 String 对象表示的字符序列与参数字符串所表示的字符序列进行比较
						 */
						result = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString()); // 倒序
					} else {
						result = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString()); // 正序
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return result;
			}
		});
	}
	
	/**
	 * map转实体
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)   
            return null;    
        Object obj = beanClass.newInstance();  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }  
        return obj;  
    }  
	/**
	 * 实体转map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
        Map<String, Object> map = new HashMap<String, Object>();   
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;
            if(value!=null&&!value.equals(""))
            	map.put(key, value);  
        }    
        return map;  
    }
}
