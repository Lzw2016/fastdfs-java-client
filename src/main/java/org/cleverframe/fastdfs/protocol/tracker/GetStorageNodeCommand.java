package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.model.StorageNode;
import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.tracker.request.GetStorageNodeByGroupNameRequest;
import org.cleverframe.fastdfs.protocol.tracker.request.GetStorageNodeRequest;

/**
 * 获取存储节点命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:16 <br/>
 */
public class GetStorageNodeCommand extends AbstractFastDFSCommand<StorageNode> {

    public GetStorageNodeCommand(String groupName) {
        super.request = new GetStorageNodeByGroupNameRequest(groupName);
        super.response = new FastDFSResponse<StorageNode>() {
        };
    }

    public GetStorageNodeCommand() {
        super.request = new GetStorageNodeRequest();
        super.response = new FastDFSResponse<StorageNode>() {
        };
    }
}
