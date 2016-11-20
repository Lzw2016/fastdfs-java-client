package org.cleverframe.fastdfs.protocol.mapper;

import org.cleverframe.fastdfs.exception.FastDFSColumnMapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 映射对象元数据,映射对象元数据必须由{@code @FastDFSColumn}注解
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:34 <br/>
 */
public class ObjectMateData {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ObjectMateData.class);

    /**
     * 映射对象类名
     */
    private String className;

    /**
     * 映射列(全部)
     */
    private List<FieldMateData> fieldList = new ArrayList<FieldMateData>();

    /**
     * 动态计算列(部分)fieldList包含dynamicFieldList
     */
    private List<FieldMateData> dynamicFieldList = new ArrayList<FieldMateData>();

    /**
     * FieldsTotalSize
     */
    private int fieldsTotalSize = 0;

    /**
     * 映射对象元数据构造函数
     */
    public <T> ObjectMateData(Class<T> genericType) {
        // 获得对象类名
        this.className = genericType.getName();
        this.fieldList = praseFieldList(genericType);
        // 校验映射定义
        validatFieldListDefine();
    }

    public String getClassName() {
        return className;
    }

    public List<FieldMateData> getFieldList() {
        return Collections.unmodifiableList(fieldList);
    }

    /**
     * 解析映射对象数据映射情况
     */
    private <T> List<FieldMateData> praseFieldList(Class<T> genericType) {
        Field[] fields = genericType.getDeclaredFields();
        List<FieldMateData> mapedFieldList = new ArrayList<FieldMateData>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FastDFSColumn.class)) {
                FieldMateData fieldMateData = new FieldMateData(field, fieldsTotalSize);
                mapedFieldList.add(fieldMateData);
                // 计算偏移量
                fieldsTotalSize += fieldMateData.getRealeSize();
                // 如果是动态计算列
                if (fieldMateData.isDynamicField()) {
                    dynamicFieldList.add(fieldMateData);
                }
            }
        }
        return mapedFieldList;
    }

    /**
     * 检查数据列定义，为了减少编码的错误，检查数据列定义是否存在列名相同或者索引定义相同(多个大于0相同的)的
     */
    private void validatFieldListDefine() {
        for (FieldMateData field : fieldList) {
            validatFieldItemDefineByIndex(field);
        }
    }

    /**
     * 检查按索引映射
     */
    private void validatFieldItemDefineByIndex(FieldMateData field) {
        for (FieldMateData otherfield : fieldList) {
            if (!field.equals(otherfield) && (field.getIndex() == otherfield.getIndex())) {
                Object[] param = {className, field.getFieldName(), otherfield.getFieldName(), field.getIndex()};
                logger.warn("在类{}映射定义中{}与{}索引定义相同为{}(请检查是否为程序错误)", param);
            }
        }
    }

    /**
     * 是否有动态数据列
     */
    private boolean hasDynamicField() {
        for (FieldMateData field : fieldList) {
            if (field.isDynamicField()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取动态数据列长度
     */
    private int getDynamicFieldSize(Object obj, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int size = 0;
        for (FieldMateData field : dynamicFieldList) {
            size = size + field.getDynamicFieldByteSize(obj, charset);
        }
        return size;
    }

    /**
     * 获取固定参数对象总长度
     */
    public int getFieldsFixTotalSize() {
        if (hasDynamicField()) {
            throw new FastDFSColumnMapException(
                    className + " class hasDynamicField, unsupport operator getFieldsTotalSize");
        }
        return fieldsTotalSize;
    }

    /**
     * 获取需要发送的报文长度
     */
    public int getFieldsSendTotalByteSize(Object bean, Charset charset) {
        if (!hasDynamicField()) {
            return fieldsTotalSize;
        } else {
            return getDynamicTotalFieldSize(bean, charset);
        }
    }

    /**
     * 获取动态属性长度
     */
    private int getDynamicTotalFieldSize(Object bean, Charset charset) {
        try {
            int dynamicFieldSize = getDynamicFieldSize(bean, charset);
            return fieldsTotalSize + dynamicFieldSize;
        } catch (NoSuchMethodException ie) {
            logger.debug("Cannot invoke get methed: ", ie);
            throw new FastDFSColumnMapException(ie);
        } catch (IllegalAccessException iae) {
            logger.debug("Illegal access: ", iae);
            throw new FastDFSColumnMapException(iae);
        } catch (InvocationTargetException ite) {
            logger.debug("Cannot invoke method: ", ite);
            throw new FastDFSColumnMapException(ite);
        }
    }

    /**
     * 导出调试信息
     */
    public void dumpObjectMateData() {
        logger.debug("dump class={}", className);
        logger.debug("----------------------------------------");
        for (FieldMateData md : fieldList) {
            logger.debug(md.toString());
        }
    }
}
