package cn.sky999.common.bean;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.dozer.loader.api.TypeMappingOptions.mapEmptyString;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;

/**
 * 映射Bean属性类
 * Created by JUN on 2017/9/12.
 */
public class BeanMapper {

    /**
     * DozerBeanMapper 对象之间相同属性名赋值
     */
    private static DozerBeanMapper dozer=new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型
     * @param source
     * @param destinationClass
     * @return
     */
    public static <T> T map(Object source,Class<T> destinationClass){
        if(source == null){
            return null;
        }
        return dozer.map(source, destinationClass);
    }
    /**
     * mapList:(基于Dozer转换Collection中对象的类型). <br/>
     * @author JUN
     * @param sourceList
     * @param destinationClass
     * @return
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass){
        if(sourceList==null){
            return null;
        }
        List<T> destinationList= Lists.newArrayList();
        for(Object sourceObject:sourceList){
            T destinationObject=dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;

    }

    /**
     * mapSet:(基于Dozer转换Set中对象的类型). <br/>
     *
     * @author JUN
     * @param sourceList
     * @param destinationClass
     * @return
     */
    public static <T> Set<T> mapSet(Collection<?> sourceList , Class<T> destinationClass){
        if(sourceList==null){
            return null;
        }
        Set<T> destinationList= Sets.newHashSet();
        for(Object sourceObject : sourceList){
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);

        }
        return destinationList;
    }

    /**
     * copy:(基于Dozer将对象source的值拷贝到对象destinationObject中). <br/>
     *
     * @author JUN
     * @param source
     * @param destinationObject
     */
    public static void copy(Object source,Object destinationObject){
        dozer.map(source, destinationObject);
    }

    /**
     * 将任意vo转化成map
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> convert2Map(T t){
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] methods = t.getClass().getMethods();
        try {
            for (Method method : methods) {
                Class<?>[] paramClass = method.getParameterTypes();
                if (paramClass.length > 0) { // 如果方法带参数，则跳过
                    continue;
                }
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    Object value = method.invoke(t);
                    String a=methodName.substring(4);
                    String b=methodName.substring(3,4);
                    result.put(b.toLowerCase().concat(a), value);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 复制不为空的属性
     * @param sources
     * @param destination
     */
    public static void copyNotNull(final Object sources, final Object destination) {
        dozer.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(sources.getClass(), destination.getClass(), mapNull(false), mapEmptyString(false));
            }
        });
        dozer.map(sources, destination);
    }
}
