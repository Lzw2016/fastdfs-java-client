package org.cleverframe.fastdfs.exception;

/**
 * 非FastDFS本身的错误码抛出的异常，而是java客户端向服务端发送命令、文件或从服务端读取结果、下载文件时发生io异常
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:44 <br/>
 */
public class FastDFSIOException extends FastDFSException {
    public FastDFSIOException(Throwable cause) {
        super("客户端连接服务端出现了io异常", cause);
    }

    public FastDFSIOException(String messge, Throwable cause) {
        super("客户端连接服务端出现了io异常:" + messge, cause);
    }

    public FastDFSIOException(String message) {
        super(message);
    }
}