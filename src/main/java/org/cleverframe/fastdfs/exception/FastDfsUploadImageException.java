package org.cleverframe.fastdfs.exception;

/**
 * 上传图片异常
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:47 <br/>
 */
public class FastDfsUploadImageException extends FastDfsException {
    protected FastDfsUploadImageException(String message) {
        super(message);
    }

    public FastDfsUploadImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
