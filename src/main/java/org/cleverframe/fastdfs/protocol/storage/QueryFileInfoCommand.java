package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.model.FileInfo;
import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.storage.request.QueryFileInfoRequest;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 18:34 <br/>
 */
public class QueryFileInfoCommand extends AbstractFastDFSCommand<FileInfo> {

    /**
     * 文件上传命令
     *
     * @param groupName 组名称
     * @param path      文件路径
     */
    public QueryFileInfoCommand(String groupName, String path) {
        super();
        this.request = new QueryFileInfoRequest(groupName, path);
        // 输出响应
        this.response = new FastDFSResponse<FileInfo>() {
        };
    }
}