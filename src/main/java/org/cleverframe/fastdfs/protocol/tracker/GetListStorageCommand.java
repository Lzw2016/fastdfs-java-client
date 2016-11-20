package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.model.StorageState;
import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.tracker.request.GetListStorageRequest;
import org.cleverframe.fastdfs.protocol.tracker.response.GetListStorageResponse;

import java.util.List;

/**
 * 获取Storage服务器状态命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:41 <br/>
 */
public class GetListStorageCommand extends AbstractFastDFSCommand<List<StorageState>> {

    public GetListStorageCommand(String groupName, String storageIpAddr) {
        super.request = new GetListStorageRequest(groupName, storageIpAddr);
        super.response = new GetListStorageResponse();
    }

    public GetListStorageCommand(String groupName) {
        super.request = new GetListStorageRequest(groupName);
        super.response = new GetListStorageResponse();
    }
}
