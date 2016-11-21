package org.cleverframe.fastdfs.protocol;

import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.exception.FastDfsServerException;
import org.cleverframe.fastdfs.utils.BytesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * FastDFS 协议头(协议头一共10位) 用于解析报文头
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:02 <br/>
 */
public class ProtocolHead {

    /**
     * 报文长度
     */
    private static final int HEAD_LENGTH = OtherConstants.FDFS_PROTO_PKG_LEN_SIZE + 2;

    /**
     * 报文内容长度1-7位
     */
    private long contentLength = 0;

    /**
     * 报文类型8位
     */
    private byte cmd;

    /**
     * 处理状态9位
     */
    private byte status = 0;

    /**
     * 请求报文构造函数
     */
    public ProtocolHead(byte cmd) {
        this.cmd = cmd;
    }

    /**
     * 返回报文构造函数
     *
     * @param contentLength 报文内容长度1-7位
     * @param cmd           报文类型8位
     * @param status        处理状态9位
     */
    private ProtocolHead(long contentLength, byte cmd, byte status) {
        this.contentLength = contentLength;
        this.cmd = cmd;
        this.status = status;
    }

    /**
     * 读取输入流创建报文头(解析报文头信息)
     *
     * @param ins 输入流
     * @return FastDFS报文头
     * @throws IOException 操作异常
     */
    static ProtocolHead createFromInputStream(InputStream ins) throws IOException {
        byte[] header = new byte[HEAD_LENGTH];
        int bytes;
        // 读取HEAD_LENGTH长度的输入流
        if ((bytes = ins.read(header)) != header.length) {
            throw new IOException("接收数据包大小不等于请求头中指定的大小 " + bytes + " != " + header.length);
        }
        long returnContentLength = BytesUtil.buff2long(header, 0);
        byte returnCmd = header[OtherConstants.PROTO_HEADER_CMD_INDEX];
        byte returnStatus = header[OtherConstants.PROTO_HEADER_STATUS_INDEX];
        // 返回解析出来的ProtoHead
        return new ProtocolHead(returnContentLength, returnCmd, returnStatus);
    }

    /**
     * toByte
     */
    byte[] toByte() {
        byte[] header;
        byte[] hex_len;
        header = new byte[HEAD_LENGTH];
        Arrays.fill(header, (byte) 0);
        hex_len = BytesUtil.long2buff(contentLength);
        System.arraycopy(hex_len, 0, header, 0, hex_len.length);
        header[OtherConstants.PROTO_HEADER_CMD_INDEX] = cmd;
        header[OtherConstants.PROTO_HEADER_STATUS_INDEX] = status;
        return header;
    }

    /**
     * 验证服务端返回报文有效性
     *
     * @return 返回true表示有效
     * @throws IOException 操作异常
     */
    boolean validateResponseHead() throws IOException {
        // 检查是否是正确反馈报文
        if (cmd != CmdConstants.FDFS_PROTO_CMD_RESP) {
            throw new IOException("接收命令: [" + cmd + "] 不正确, 应该是: [" + CmdConstants.FDFS_PROTO_CMD_RESP + "]");
        }
        // 获取处理错误状态
        if (status != 0) {
            throw FastDfsServerException.byCode(status);
        }
        if (contentLength < 0) {
            throw new IOException("接收内容长度小于0: " + contentLength + " < 0!");
        }
        return true;
    }

    long getContentLength() {
        return contentLength;
    }

    void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public byte getCmd() {
        return cmd;
    }

    public byte getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ProtoHead [contentLength=" + contentLength + ", cmd=" + cmd + ", status=" + status + "]";
    }
}
