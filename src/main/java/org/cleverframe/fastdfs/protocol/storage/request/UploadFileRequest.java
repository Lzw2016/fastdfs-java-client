package org.cleverframe.fastdfs.protocol.storage.request;

import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.protocol.FastDFSRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.protocol.mapper.FastDFSColumn;

import java.io.InputStream;

/**
 * 文件上传命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:00 <br/>
 */
public class UploadFileRequest extends FastDFSRequest {
    private static final byte uploadCmd = CmdConstants.STORAGE_PROTO_CMD_UPLOAD_FILE;
    private static final byte uploadAppenderCmd = CmdConstants.STORAGE_PROTO_CMD_UPLOAD_APPENDER_FILE;

    /**
     * 存储节点index
     */
    @FastDFSColumn(index = 0)
    private byte storeIndex;

    /**
     * 发送文件长度
     */
    @FastDFSColumn(index = 1)
    private long fileSize;

    /**
     * 文件扩展名
     */
    @FastDFSColumn(index = 2, max = OtherConstants.FDFS_FILE_EXT_NAME_MAX_LEN)
    private String fileExtName;

    /**
     * 构造函数
     *
     * @param storeIndex     存储节点
     * @param inputStream    输入流
     * @param fileExtName    文件扩展名
     * @param fileSize       文件大小
     * @param isAppenderFile 是否支持断点续传
     */
    public UploadFileRequest(byte storeIndex, InputStream inputStream, String fileExtName, long fileSize, boolean isAppenderFile) {
        super();
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.storeIndex = storeIndex;
        this.fileExtName = fileExtName;
        if (isAppenderFile) {
            head = new ProtocolHead(uploadAppenderCmd);
        } else {
            head = new ProtocolHead(uploadCmd);
        }
    }

    public byte getStoreIndex() {
        return storeIndex;
    }

    public void setStoreIndex(byte storeIndex) {
        this.storeIndex = storeIndex;
    }

    public String getFileExtName() {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }
}
