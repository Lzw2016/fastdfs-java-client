package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.tracker.request.DeleteStorageRequest;

/**
 * 删除存储服务器
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:43 <br/>
 */
public class DeleteStorageCommand extends AbstractFastDFSCommand<Void> {

    public DeleteStorageCommand(String groupName, String storageIpAddr) {
        super.request = new DeleteStorageRequest(groupName, storageIpAddr);
        super.response = new FastDFSResponse<Void>() {
        };
    }
}
