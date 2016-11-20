package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.model.GroupState;
import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.tracker.request.GetGroupListRequest;
import org.cleverframe.fastdfs.protocol.tracker.response.GetGroupListResponse;

import java.util.List;

/**
 * 获取Group信息命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:05 <br/>
 */
public class GetGroupListCommand extends AbstractFastDFSCommand<List<GroupState>> {

    public GetGroupListCommand() {
        super.request = new GetGroupListRequest();
        super.response = new GetGroupListResponse();
    }
}
