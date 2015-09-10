package com.huobanplus.goodsync.datacenter.common;

import javax.persistence.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liual on 2015-09-09.
 */
public class ClassHandler {
    public static void ClassCopy(Object original, Object target) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class originalClass = original.getClass();
        Class targetClass = target.getClass();
        Method[] originalMethods = originalClass.getMethods();
        Method[] targetMethods = targetClass.getMethods();

        //得到注解是id的成员变量
        Field[] fields = originalClass.getDeclaredFields();
        Field idField = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            }
        }
        String filesName = idField.getName().substring(0, 1).toUpperCase() + idField.getName().substring(1);

        List<Method> originalGet = new ArrayList<>();
        List<Method> targetSet = new ArrayList<>();
        for (Method method : originalMethods) {

            if (method.getName().startsWith("get") && !method.getName().equals("get" + filesName)) {
                originalGet.add(method);
            }
        }
        for (Method method : targetMethods) {
            if (method.getName().startsWith("set") && !method.getName().equals("set" + filesName)) {
                targetSet.add(method);
            }
        }
        for (Method getMethod : originalGet) {
            String name = getMethod.getName().substring(3);
            for (Method setMethod : targetSet) {
                if (setMethod.getName().substring(3).equals(name)) {
                    setMethod.invoke(target, getMethod.invoke(original));
                }
            }
        }
    }
}
