package cn.sky999.util;

import org.apache.commons.lang.StringUtils;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import java.lang.reflect.Field;
import java.util.*;

/**
 * IK表达式
 * expression：表达式公式
 * object：map或者实体类
 * 返回result Object类型，实际是字符串。
 */
public class IKExpressionUtil {
    public static  Object countResult(String expression,Object obj) throws Exception{
        if(StringUtils.isBlank(expression))
            return null;
        List<Variable> variables = new ArrayList<Variable>();
        if(obj != null){
            if(obj instanceof Map){
                Iterator entries = ((Map)obj).entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    variables.add(Variable.createVariable((String)entry.getKey(), entry.getValue()));
                }
            }else{
                Class cls = (Class) obj.getClass();
                Field[] fs = cls.getDeclaredFields();
                for(Field f:fs){
                    f.setAccessible(true);
                    variables.add(Variable.createVariable(f.getName(),f.get(obj)));
                }
            }
        }
        Object result = ExpressionEvaluator.evaluate(expression, variables);
        return result;
    }
}
