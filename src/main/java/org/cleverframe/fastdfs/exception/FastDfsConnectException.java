package org.cleverframe.fastdfs.exception;

/**
 * 非FastDFS本身的错误码抛出的异常，socket连不上时抛出的异常
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:41 <br/>
 */
public class FastDfsConnectException extends FastDfsUnavailableException {
    public FastDfsConnectException(String message, Throwable t) {
        super(message, t);
    }
}