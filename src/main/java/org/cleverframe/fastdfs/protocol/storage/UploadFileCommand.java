package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.BaseResponse;
import org.cleverframe.fastdfs.protocol.storage.request.UploadFileRequest;

import java.io.InputStream;

/**
 * 文件上传命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 18:59 <br/>
 */
public class UploadFileCommand extends StorageCommand<StorePath> {

    /**
     * 文件上传命令
     *
     * @param storeIndex     存储节点
     * @param inputStream    输入流
     * @param fileExtName    文件扩展名
     * @param fileSize       文件大小
     * @param isAppenderFile 是否支持断点续传
     */
    public UploadFileCommand(byte storeIndex, InputStream inputStream, String fileExtName, long fileSize, boolean isAppenderFile) {
        super();
        this.request = new UploadFileRequest(storeIndex, inputStream, fileExtName, fileSize, isAppenderFile);
        // 输出响应
        this.response = new BaseResponse<StorePath>() {
        };
    }
}