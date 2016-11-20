package org.cleverframe.fastdfs.protocol.storage.request;

import org.cleverframe.fastdfs.constant.CmdConstants;
import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.protocol.BaseRequest;
import org.cleverframe.fastdfs.protocol.ProtocolHead;
import org.cleverframe.fastdfs.mapper.DynamicFieldType;
import org.cleverframe.fastdfs.mapper.FastDFSColumn;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 17:05 <br/>
 */
public class DeleteFileRequest extends BaseRequest {
    /**
     * 组名
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 路径名
     */
    @FastDFSColumn(index = 1, dynamicField = DynamicFieldType.allRestByte)
    private String path;

    /**
     * 删除文件命令
     *
     * @param groupName 组名
     * @param path      文件路径
     */
    public DeleteFileRequest(String groupName, String path) {
        super();
        this.groupName = groupName;
        this.path = path;
        this.head = new ProtocolHead(CmdConstants.STORAGE_PROTO_CMD_DELETE_FILE);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
