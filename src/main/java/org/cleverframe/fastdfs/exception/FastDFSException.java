package org.cleverframe.fastdfs.exception;

/**
 * FastDFS客户端异常 基类
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:22 <br/>
 */
public class FastDFSException extends RuntimeException {
    public FastDFSException(String message) {
        super(message);
    }

    public FastDFSException(String message, Throwable cause) {
        super(message, cause);
    }
}

