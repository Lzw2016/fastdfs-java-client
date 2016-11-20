package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.protocol.BaseResponse;
import org.cleverframe.fastdfs.protocol.storage.request.DeleteFileRequest;

/**
 * 删除文件爱你命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 17:03 <br/>
 */
public class DeleteFileCommand extends StorageCommand<Void> {
    /**
     * 文件删除命令
     *
     * @param groupName 组名
     * @param path      文件路径
     */
    public DeleteFileCommand(String groupName, String path) {
        super();
        this.request = new DeleteFileRequest(groupName, path);
        // 输出响应
        this.response = new BaseResponse<Void>() {
        };
    }
}
