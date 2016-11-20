package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.storage.request.TruncateRequest;

/**
 * 文件Truncate命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 18:53 <br/>
 */
public class TruncateCommand extends AbstractFastDFSCommand<Void> {

    /**
     * 文件Truncate命令
     *
     * @param path     文件路径
     * @param fileSize 文件大小
     */
    public TruncateCommand(String path, long fileSize) {
        super();
        this.request = new TruncateRequest(path, fileSize);
        // 输出响应
        this.response = new FastDFSResponse<Void>() {
        };
    }
}
