package org.cleverframe.fastdfs.protocol;

import org.cleverframe.fastdfs.protocol.mapper.ObjectMateData;
import org.cleverframe.fastdfs.utils.FastDFSParamMapperUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * FastDFS操作请求 基类
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 1:00 <br/>
 */
public abstract class FastDFSRequest {
    /**
     * 报文头
     */
    protected ProtocolHead head;

    /**
     * 发送文件流
     */
    protected InputStream inputFile;

    /**
     * 获取报文头(包内可见)
     */
    ProtocolHead getHead() {
        return head;
    }

    /**
     * 获取报文头
     */
    byte[] getHeadByte(Charset charset) {
        // 设置报文长度
        head.setContentLength(getBodyLength(charset));
        // 返回报文byte
        return head.toByte();
    }

    /**
     * 打包参数
     */
    protected byte[] encodeParam(Charset charset) {
        return FastDFSParamMapperUtils.toByte(this, charset);
    }

    /**
     * 获取参数域长度
     */
    private long getBodyLength(Charset charset) {
        ObjectMateData objectMateData = FastDFSParamMapperUtils.getObjectMap(this.getClass());
        return objectMateData.getFieldsSendTotalByteSize(this, charset) + getFileSize();
    }

    InputStream getInputFile() {
        return inputFile;
    }

    public long getFileSize() {
        return 0;
    }
}
