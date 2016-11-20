package org.cleverframe.fastdfs.protocol.storage.request;

import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.protocol.FastDFSRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.protocol.mapper.DynamicFieldType;
import org.cleverframe.fastdfs.protocol.mapper.FastDFSColumn;

import java.io.InputStream;

/**
 * 从文件上传命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:09 <br/>
 */
public class UploadSlaveFileRequest extends FastDFSRequest {
    /**
     * 主文件名长度
     */
    @FastDFSColumn(index = 0)
    private long masterFileNameSize;

    /**
     * 发送文件长度
     */
    @FastDFSColumn(index = 1)
    private long fileSize;

    /**
     * 名称前缀
     */
    @FastDFSColumn(index = 2, max = OtherConstants.FDFS_FILE_PREFIX_MAX_LEN)
    private final String prefixName;

    /**
     * 文件扩展名
     */
    @FastDFSColumn(index = 3, max = OtherConstants.FDFS_FILE_EXT_NAME_MAX_LEN)
    private String fileExtName;

    /**
     * 主文件名
     */
    @FastDFSColumn(index = 4, dynamicField = DynamicFieldType.allRestByte)
    private final String masterFilename;

    /**
     * 构造函数
     *
     * @param inputStream    输入流
     * @param fileSize       文件大小
     * @param masterFilename 主文件名称(路径)
     * @param prefixName     从文件前缀
     * @param fileExtName    文件扩展名
     */
    public UploadSlaveFileRequest(InputStream inputStream, long fileSize, String masterFilename, String prefixName, String fileExtName) {
        super();
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.masterFileNameSize = masterFilename.length();
        this.masterFilename = masterFilename;
        this.fileExtName = fileExtName;
        this.prefixName = prefixName;
        head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_UPLOAD_SLAVE_FILE);
    }

    public long getMasterFileNameSize() {
        return masterFileNameSize;
    }

    public void setMasterFileNameSize(long masterFileNameSize) {
        this.masterFileNameSize = masterFileNameSize;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public String getMasterFilename() {
        return masterFilename;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
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
