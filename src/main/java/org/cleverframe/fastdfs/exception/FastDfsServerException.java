package org.cleverframe.fastdfs.exception;

import org.cleverframe.fastdfs.constant.ErrorCodeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:22 <br/>
 */
public class FastDfsServerException extends FastDfsException {
    /**
     * 错误对照表
     */
    private static final Map<Integer, String> CODE_MESSAGE_MAPPING;

    static {
        Map<Integer, String> mapping = new HashMap<Integer, String>();
        mapping.put((int) ErrorCodeConstants.ERR_NO_ENOENT, "找不到节点或文件");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EIO, "服务端发生io异常");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EINVAL, "无效的参数");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EBUSY, "服务端忙");
        mapping.put((int) ErrorCodeConstants.ERR_NO_ENOSPC, "没有足够的存储空间");
        mapping.put((int) ErrorCodeConstants.ERR_NO_CONNREFUSED, "服务端拒绝连接");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EALREADY, "文件已经存在？");
        CODE_MESSAGE_MAPPING = Collections.unmodifiableMap(mapping);
    }

    private int errorCode;

    private FastDfsServerException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static FastDfsServerException byCode(int errorCode) {
        String message = CODE_MESSAGE_MAPPING.get(errorCode);
        if (message == null) {
            message = "未知错误";
        }
        message = "错误码：" + errorCode + "，错误信息：" + message;
        return new FastDfsServerException(errorCode, message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}