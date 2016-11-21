package org.cleverframe.fastdfs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

/**
 * 反射工具类(自己实现的)<br/>
 * 1.提供调用getter/setter方法<br/>
 * 2.访问私有变量<br/>
 * 3.调用私有方法<br/>
 * 4.获取泛型类型Class<br/>
 * 5.被AOP过的真实类等工具函数<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-29 23:56 <br/>
 */
public class ReflectionsUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ReflectionsUtils.class);

    /**
     * set方法前缀
     */
    private static final String SETTER_PREFIX = "set";

    /**
     * get方法前缀
     */
    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * 将checked exception转换为unchecked exception
     *
     * @param e checked exception
     * @return 返回RuntimeException
     */
    private static RuntimeException convertReflectionExceptionToUnchecked(Throwable e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     *
     * @param method 目标方法实例
     */
    private static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     *
     * @param field 成员变量实例
     */
    private static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 在给定的对象中查找指定的方法，获取不到就在其父类中找(循环向上转型)，获取到方法之后强行设置成public返回<br/>
     * 如向上转型到Object仍无法找到, 返回null<br/>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)<br/>
     *
     * @param obj            目标对象
     * @param methodName     方法名称
     * @param parameterTypes 方法签名参数类型
     * @return 方法实例，获取失败返回null
     */
    private static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        Class<?> searchType = obj.getClass();
        while (searchType != Object.class) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                if (method != null) {
                    // 强制设置方法可以访问(public)
                    makeAccessible(method);
                    return method;
                } else {
                    continue;
                }
            } catch (Throwable e) {
                logger.error("getAccessibleMethod-反射错误，找不到方法", e);
                // Method不在当前类定义,继续向上转型
            }
            // 获取父类类型，继续查找方法
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * 在给定的对象中查找指定的方法，获取不到就在其父类中找(循环向上转型)，获取到方法之后强行设置成public返回<br/>
     * 如向上转型到Object仍无法找到, 返回null<br/>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)<br/>
     *
     * @param obj        目标对象
     * @param methodName 方法名称
     * @return 方法实例，获取失败返回null
     */
    private static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        Class<?> searchType = obj.getClass();
        while (searchType != Object.class) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    // 强制设置方法可以访问(public)
                    makeAccessible(method);
                    return method;
                }
            }
            // 获取父类类型，继续查找方法
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * 在给定的对象中查找指定的成员变量，获取不到就在其父类中找(循环向上转型)，获取到之后强行设置成public返回<br/>
     *
     * @param obj       目标对象
     * @param fieldName 成员变量名称
     * @return 成员变量实例
     */
    private static Field getAccessibleField(final Object obj, final String fieldName) {
        Class<?> superClass = obj.getClass();
        while (superClass != Object.class) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                if (field != null) {
                    // 强制设置成员变量可以访问(public)
                    makeAccessible(field);
                    return field;
                } else {
                    continue;
                }
            } catch (Throwable e) {
                logger.error("getAccessibleField-反射错误，找不到字段", e);
                // Field不在当前类定义,继续向上转型
            }
            // 获取父类类型，继续查找字段
            superClass = superClass.getSuperclass();
        }
        return null;
    }


    /**
     * 直接调用对象方法, 无视private/protected修饰符<br/>
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用<br/>
     *
     * @param obj            目标对象
     * @param methodName     方法名称
     * @param parameterTypes 方法签名参数类型
     * @param args           调用方法参数值
     * @return 返回方法调用返回结果
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("invokeMethod-在对象[" + obj + "]中找不到方法 [" + methodName + "]");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invokeMethod-反射调用方法失败", e);
            throw convertReflectionExceptionToUnchecked(e);
        }
    }


    /**
     * 直接调用对象方法, 无视private/protected修饰符<br/>
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用<br/>
     * 只匹配方法名，如果有多个相同的方法名，则只调用第一个<br/>
     *
     * @param obj        目标对象
     * @param methodName 方法名称
     * @param args       方法签名参数类型
     * @return 返回方法调用返回结果
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("invokeMethod-在对象[" + obj + "]中找不到方法 [" + methodName + "]");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invokeMethodByName-反射调用方法失败", e);
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 反射调用调用Getter方法
     *
     * @param obj          目标对象
     * @param propertyName 属性名，支持多级，如：对象名.对象名.方法
     * @return 调用结果
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        String[] propertyArray = StringUtils.split(propertyName, ".");
        for (String name : propertyArray) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
        }
        return object;
    }

    /**
     * 反射调用Setter方法
     *
     * @param obj          目标对象
     * @param propertyName 属性名，支持多级，如：对象名.对象名.方法
     * @param value        参数值
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[]{value});
            }
        }
    }

    /**
     * 通过反射直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数
     *
     * @param obj       目标对象
     * @param fieldName 属性名，不支持：属性名.属性名.属性名...
     * @return 属性值
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("getFieldValue-在对象[" + obj + "]中找不到字段[" + fieldName + "]");
        }
        Object result;
        try {
            result = field.get(obj);
        } catch (Throwable e) {
            logger.error("getFieldValue-通过反射直接读取对象属性值失败", e);
            throw convertReflectionExceptionToUnchecked(e);
        }
        return result;
    }

    /**
     * 通过反射直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数
     *
     * @param obj       目标对象
     * @param fieldName 属性名，不支持：属性名.属性名.属性名...
     * @param value     属性值
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("getFieldValue-在对象[" + obj + "]中找不到字段[" + fieldName + "]");
        }
        try {
            field.set(obj, value);
        } catch (Throwable e) {
            logger.error("getFieldValue-通过反射直接设置对象属性值", e);
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型<br/>
     * 如无法找到, 返回Object.class<br/>
     * 如：public UserDao extends HibernateDao&lt;User&gt;
     *
     * @param clazz 目标Class
     * @param index 泛型类型所处的位置，例如：直接父类的泛型使用0
     * @return the 返回父类层级中的泛型类型，如无法找到, 返回Object.class
     */
    public static Class getClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "类不是ParameterizedType的子类");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            if (params[index] instanceof ParameterizedType) {
                Type type = ((ParameterizedType) params[index]).getRawType();
                if (type instanceof Class) {
                    return (Class) type;
                }
            }
            logger.warn(clazz.getSimpleName() + "没有设置超泛型参数的实际类");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处<br/>
     * 如无法找到, 返回Object.class<br/>
     * 如：public UserDao extends HibernateDao&lt;User&gt;
     *
     * @param clazz 目标Class
     * @return 返回父类中的泛型类型，如无法找到, 返回Object.class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 获取实际使用的类，获取被AOP过的真实类
     *
     * @param instance 目标对象
     * @return 返回实际使用的类
     */
    public static Class<?> getUserClass(Object instance) {
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }
}
