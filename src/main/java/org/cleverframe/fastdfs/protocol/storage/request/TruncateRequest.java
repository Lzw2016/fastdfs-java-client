package org.cleverframe.fastdfs.protocol.storage.request;

import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.protocol.FastDFSRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.protocol.mapper.DynamicFieldType;
import org.cleverframe.fastdfs.protocol.mapper.FastDFSColumn;

import java.nio.charset.Charset;

/**
 * 文件Truncate命令<br/>
 * 使用限制：创建文件时候需要采用[源追加]模式,之后才能Truncate<br/>
 * size使用也有限制<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 18:56 <br/>
 */
public class TruncateRequest extends FastDFSRequest {
    /**
     * 文件路径长度
     */
    @FastDFSColumn(index = 0)
    private long pathSize;
    /**
     * 截取文件长度
     */
    @FastDFSColumn(index = 1)
    private long fileSize;
    /**
     * 文件路径
     */
    @FastDFSColumn(index = 2, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 文件Truncate命令
     *
     * @param path     文件路径
     * @param fileSize 截取文件长度
     */
    public TruncateRequest(String path, long fileSize) {
        super();
        this.fileSize = fileSize;
        this.path = path;
        head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_TRUNCATE_FILE);
    }

    /**
     * 打包参数
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        // 运行时参数在此计算值
        this.pathSize = path.getBytes(charset).length;
        return super.encodeParam(charset);
    }

    public long getPathSize() {
        return pathSize;
    }

    public void setPathSize(long pathSize) {
        this.pathSize = pathSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return "TruncateRequest [pathSize=" + pathSize + ", fileSize=" + fileSize + ", path=" + path + "]";
    }
}
