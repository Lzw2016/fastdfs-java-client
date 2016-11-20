package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.model.StorageNodeInfo;
import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.tracker.request.GetFetchStorageRequest;

/**
 * 获取文件源存储服务器
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:30 <br/>
 */
public class GetFetchStorageCommand extends AbstractFastDFSCommand<StorageNodeInfo> {

    /**
     * 获取文件源服务器
     *
     * @param groupName 组名称
     * @param path      路径
     * @param toUpdate  toUpdate
     */
    public GetFetchStorageCommand(String groupName, String path, boolean toUpdate) {
        super.request = new GetFetchStorageRequest(groupName, path, toUpdate);
        super.response = new FastDFSResponse<StorageNodeInfo>() {
        };
    }
}
