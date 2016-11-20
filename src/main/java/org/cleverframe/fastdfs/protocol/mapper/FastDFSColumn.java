package org.cleverframe.fastdfs.protocol.mapper;


import java.lang.annotation.*;

/**
 * 传输参数定义标签
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:35 <br/>
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FastDFSColumn {
    /**
     * 映射顺序(从0开始)
     */
    int index() default 0;

    /**
     * String最长度
     */
    int max() default 0;

    /**
     * 动态属性
     */
    DynamicFieldType dynamicField() default DynamicFieldType.NULL;
}
