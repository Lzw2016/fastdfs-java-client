package org.cleverframe.fastdfs.exception;

/**
 * FastDFS客户端异常 基类
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:22 <br/>
 */
public class FastDfsException extends RuntimeException {
    public FastDfsException(String message) {
        super(message);
    }

    public FastDfsException(String message, Throwable cause) {
        super(message, cause);
    }
}

