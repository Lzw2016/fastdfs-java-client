package org.cleverframe.fastdfs.protocol.mapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.exception.FastDFSColumnMapException;
import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.utils.BytesUtil;
import org.cleverframe.fastdfs.utils.MetadataMapperUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.Set;

/**
 * 属性映射MateData定义
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:48 <br/>
 */
public class FieldMateData {

    /**
     * 列
     */
    private Field field;

    /**
     * 列索引
     */
    private int index;

    /**
     * 单元最大长度
     */
    private int max;

    /**
     * 单元长度
     */
    private int size;

    /**
     * 列偏移量
     */
    private int offsize;

    /**
     * 动态属性类型
     */
    DynamicFieldType dynamicFieldType;

    /**
     * 构造函数
     */
    public FieldMateData(Field mapedfield, int offsize) {
        FastDFSColumn column = mapedfield.getAnnotation(FastDFSColumn.class);
        this.field = mapedfield;
        this.index = column.index();
        this.max = column.max();
        this.size = getFieldSize(field);
        this.offsize = offsize;
        this.dynamicFieldType = column.dynamicField();
        // 如果强制设置了最大值，以最大值为准
        if (this.max > 0 && this.size > this.max) {
            this.size = this.max;
        }
    }

    /**
     * 获取Field大小
     */
    private int getFieldSize(Field field) {
        if (String.class == field.getType()) {
            return this.max;
        } else if (long.class == field.getType()) {
            return OtherConstants.FDFS_PROTO_PKG_LEN_SIZE;
        } else if (int.class == field.getType()) {
            return OtherConstants.FDFS_PROTO_PKG_LEN_SIZE;
        } else if (java.util.Date.class == field.getType()) {
            return OtherConstants.FDFS_PROTO_PKG_LEN_SIZE;
        } else if (byte.class == field.getType()) {
            return 1;
        } else if (boolean.class == field.getType()) {
            return 1;
        } else if (Set.class == field.getType()) {
            return 0;
        }
        throw new FastDFSColumnMapException(field.getName() + "获取Field大小时未识别的FastDFSColumn类型" + field.getType());
    }

    /**
     * 获取值
     */
    public Object getValue(byte[] bs, Charset charset) {
        if (String.class == field.getType()) {
            if (isDynamicField()) {
                return (new String(bs, offsize, bs.length - offsize, charset)).trim();
            }
            return (new String(bs, offsize, size, charset)).trim();
        } else if (long.class == field.getType()) {
            return BytesUtil.buff2long(bs, offsize);
        } else if (int.class == field.getType()) {
            return (int) BytesUtil.buff2long(bs, offsize);
        } else if (java.util.Date.class == field.getType()) {
            return new Date(BytesUtil.buff2long(bs, offsize) * 1000);
        } else if (byte.class == field.getType()) {
            return bs[offsize];
        } else if (boolean.class == field.getType()) {
            return bs[offsize] != 0;
        }
        throw new FastDFSColumnMapException(field.getName() + "获取值时未识别的FdfsColumn类型" + field.getType());
    }

    /**
     * 获取真实属性
     */
    public int getRealeSize() {
        // 如果是动态属性
        if (isDynamicField()) {
            return 0;
        }
        return size;
    }

    /**
     * 将属性值转换为byte
     */
    public byte[] toByte(Object bean, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getFieldValue(bean);
        if (isDynamicField()) {
            return getDynamicFieldByteValue(value, charset);
        } else if (String.class.equals(field.getType())) {
            // 如果是动态属性
            return BytesUtil.objString2Byte((String) value, max, charset);
        } else if (long.class.equals(field.getType())) {
            return BytesUtil.long2buff((Long) value);
        } else if (int.class.equals(field.getType())) {
            return BytesUtil.long2buff((Integer) value);
        } else if (Date.class.equals(field.getType())) {
            throw new FastDFSColumnMapException("Date 还不支持");
        } else if (byte.class.equals(field.getType())) {
            byte[] result = new byte[1];
            result[0] = (Byte) value;
            return result;
        } else if (boolean.class.equals(field.getType())) {
            throw new FastDFSColumnMapException("boolean 还不支持");
        }
        throw new FastDFSColumnMapException("将属性值转换为byte时未识别的FdfsColumn类型" + field.getName());
    }

    /**
     * 获取动态属性值
     */
    @SuppressWarnings("unchecked")
    private byte[] getDynamicFieldByteValue(Object value, Charset charset) {
        switch (dynamicFieldType) {
            // 如果是打包剩余的所有Byte
            case allRestByte:
                return BytesUtil.objString2Byte((String) value, charset);
            // 如果是文件matedata
            case matedata:
                return MetadataMapperUtils.toByte((Set<MateData>) value, charset);
            default:
                return BytesUtil.objString2Byte((String) value, charset);
        }
    }

    /**
     * 获取单元对应值
     */
    private Object getFieldValue(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return PropertyUtils.getProperty(bean, field.getName());
    }

    /**
     * 获取动态属性长度
     */
    @SuppressWarnings("unchecked")
    public int getDynamicFieldByteSize(Object bean, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = PropertyUtils.getProperty(bean, field.getName());
        if (null == value) {
            return 0;
        }
        switch (dynamicFieldType) {
            // 如果是打包剩余的所有Byte
            case allRestByte:
                return ((String) value).getBytes(charset).length;
            // 如果是文件matedata
            case matedata:
                return MetadataMapperUtils.toByte((Set<MateData>) value, charset).length;
            default:
                return getFieldSize(field);
        }
    }

    /**
     * 是否动态属性
     */
    public boolean isDynamicField() {
        return (!DynamicFieldType.NULL.equals(dynamicFieldType));
    }

    public String getFieldName() {
        return field.getName();
    }

    public Field getField() {
        return field;
    }

    public int getIndex() {
        return index;
    }

    public int getMax() {
        return max;
    }

    public int getSize() {
        return size;
    }

    public int getOffsize() {
        return offsize;
    }

    public DynamicFieldType getDynamicFieldType() {
        return dynamicFieldType;
    }

    @Override
    public String toString() {
        return "FieldMateData{" +
                "field=" + getFieldName() +
                ", index=" + index +
                ", max=" + max +
                ", size=" + size +
                ", offsize=" + offsize +
                ", dynamicFieldType=" + dynamicFieldType +
                '}';
    }
}
