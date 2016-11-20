package org.cleverframe.fastdfs.exception;

/**
 * 上传图片异常
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:47 <br/>
 */
public class FastDFSUploadImageException extends FastDFSException {
    protected FastDFSUploadImageException(String message) {
        super(message);
    }

    public FastDFSUploadImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
