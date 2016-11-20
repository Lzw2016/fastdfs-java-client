package org.cleverframe.fastdfs.protocol.tracker.request;

import org.apache.commons.lang3.Validate;
import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.protocol.FastDFSRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.protocol.mapper.FastDFSColumn;

/**
 * 按分组获取存储节点请求(根据Group Name)
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:19 <br/>
 */
public class GetStorageNodeByGroupNameRequest extends FastDFSRequest {

    private static final byte withGroupCmd = CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE;

    /**
     * 分组定义
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private final String groupName;

    /**
     * 获取存储节点
     *
     * @param groupName 分组名称
     */
    public GetStorageNodeByGroupNameRequest(String groupName) {
        Validate.notBlank(groupName, "分组不能为空");
        this.groupName = groupName;
        this.head = new ProtocolHead(withGroupCmd);
    }

    public String getGroupName() {
        return groupName;
    }
}
