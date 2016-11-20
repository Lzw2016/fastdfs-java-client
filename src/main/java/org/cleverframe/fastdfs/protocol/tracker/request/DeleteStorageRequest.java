package org.cleverframe.fastdfs.protocol.tracker.request;

import org.apache.commons.lang3.Validate;
import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.protocol.BaseRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.mapper.FastDFSColumn;

/**
 * 删除存储服务器 请求
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:44 <br/>
 */
public class DeleteStorageRequest extends BaseRequest {
    /**
     * 组名
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 存储ip
     */
    @FastDFSColumn(index = 1, max = OtherConstants.FDFS_IPADDR_SIZE - 1)
    private String storageIpAddr;

    /**
     * 获取文件源服务器
     *
     * @param groupName     组名称
     * @param storageIpAddr 存储服务器IP地址
     */
    public DeleteStorageRequest(String groupName, String storageIpAddr) {
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(storageIpAddr, "文件路径不能为空");
        this.groupName = groupName;
        this.storageIpAddr = storageIpAddr;
        head = new ProtocolHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_DELETE_STORAGE);
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStorageIpAddr() {
        return storageIpAddr;
    }

}
