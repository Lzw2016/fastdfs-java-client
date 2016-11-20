package org.cleverframe.fastdfs.protocol.storage.request;

import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.protocol.FastDFSRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.protocol.mapper.DynamicFieldType;
import org.cleverframe.fastdfs.protocol.mapper.FastDFSColumn;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 文件上传请求
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 16:59 <br/>
 */
public class AppendFileRequest extends FastDFSRequest {
    /**
     * 文件路径长度
     */
    @FastDFSColumn(index = 0)
    private long pathSize;

    /**
     * 发送文件长度
     */
    @FastDFSColumn(index = 1)
    private long fileSize;

    /**
     * 文件路径
     */
    @FastDFSColumn(index = 2, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 构造函数
     *
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param path        文件路径
     */
    public AppendFileRequest(InputStream inputStream, long fileSize, String path) {
        super();
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.path = path;
        head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_APPEND_FILE);
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
        return "AppendFileRequest [pathSize=" + pathSize + ", fileSize=" + fileSize + ", path=" + path + "]";
    }
}
