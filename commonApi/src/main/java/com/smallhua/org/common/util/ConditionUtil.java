package com.smallhua.org.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.smallhua.org.common.domain.BaseParam;
import com.smallhua.org.common.domain.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈将BaseParam的条件放到Example中〉
 *
 * @author ZZH
 * @create 2021/5/19
 * @since 1.0.0
 */
public class ConditionUtil {

    public static void createCondition(BaseParam baseParam, Object criteria, Class clazz) {
        List<Condition> conditions = baseParam.getConditions();
        if (CollUtil.isEmpty(conditions))
            return;
        conditions.forEach(item -> generateCondition(item, criteria, clazz));
    }

    private static void generateCondition(Condition condition, Object criteria, Class clazz) {
        Assert.notNull(condition.getFieldName());
        Assert.notNull(condition.getOp());
        Assert.notNull(condition.getValues());
        //操作符
        String op = condition.getOp();
        //属性值
        List<?> values;
        //属性
        String fieldName = condition.getFieldName();

        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            values = condition.getValues().stream().map(item -> TypeUtil.tranfrom(fieldClass, item)).collect(Collectors.toList());
            renderObj(criteria, firstLetterToUpper(fieldName), op, values, fieldClass);
        } catch (NoSuchFieldException e) {
            Class superclass = clazz.getSuperclass();
            if (superclass != null) {
                generateCondition(condition, criteria, superclass);
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母转成大写
     *
     * @param fieldName
     * @return
     */
    public static String firstLetterToUpper(String fieldName) {
        return fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)), String.valueOf(fieldName.charAt(0)).toUpperCase());
    }

    private static void renderObj(Object criteria, String fieldName, String op, List<?> values, Class<?> fieldClass) throws Exception {
        String normalize_op = Normalizer.normalize(op, Normalizer.Form.NFKC);
        Class<?> aClass = criteria.getClass();
        StringBuffer sb = new StringBuffer();
        sb.append("and").append(fieldName);
        if (normalize_op.trim().equalsIgnoreCase("=")) {
            sb.append("EqualTo");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass);
            method.invoke(criteria, values.get(0));
            return;
        } else if (normalize_op.trim().equalsIgnoreCase("in")) {
            sb.append("In");
            Method method = aClass.getDeclaredMethod(sb.toString(), List.class);
            method.invoke(criteria, values);
            return;
        } else if (normalize_op.trim().equalsIgnoreCase("like")) {
            sb.append("Like");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass);
            method.invoke(criteria, values.get(0) + "%");
            return;
        } else if (normalize_op.trim().equalsIgnoreCase("between")) {
            sb.append("Between");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass, fieldClass);
            method.invoke(criteria, values.get(0), values.get(1));
            return;
        } else if (normalize_op.trim().equalsIgnoreCase(">")) {
            sb.append("GreaterThan");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass);
            method.invoke(criteria, values.get(0));
            return;
        } else if (normalize_op.trim().equalsIgnoreCase("<")) {
            sb.append("LessThan");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass, fieldClass);
            method.invoke(criteria, values.get(0), values.get(1));
            return;
        } else if (normalize_op.trim().equalsIgnoreCase("<=")) {
            sb.append("LessThanOrEqualTo");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass);
            method.invoke(criteria, values.get(0));
            return;
        } else if (normalize_op.trim().equalsIgnoreCase(">=")) {
            sb.append("GreaterThanOrEqualTo");
            Method method = aClass.getDeclaredMethod(sb.toString(), fieldClass);
            method.invoke(criteria, values.get(0));
            return;
        }
    }

}