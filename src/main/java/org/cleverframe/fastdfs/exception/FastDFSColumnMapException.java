package org.cleverframe.fastdfs.exception;

/**
 * 映射异常
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:41 <br/>
 */
public class FastDFSColumnMapException extends RuntimeException {
    public FastDFSColumnMapException() {
    }

    public FastDFSColumnMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FastDFSColumnMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastDFSColumnMapException(String message) {
        super(message);
    }

    public FastDFSColumnMapException(Throwable cause) {
        super(cause);
    }
}