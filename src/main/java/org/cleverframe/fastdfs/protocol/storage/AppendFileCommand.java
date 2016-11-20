package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.protocol.AbstractFastDFSCommand;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.storage.request.AppendFileRequest;

import java.io.InputStream;

/**
 * 添加文件命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 16:58 <br/>
 */
public class AppendFileCommand extends AbstractFastDFSCommand<Void> {

    public AppendFileCommand(InputStream inputStream, long fileSize, String path) {
        this.request = new AppendFileRequest(inputStream, fileSize, path);
        // 输出响应
        this.response = new FastDFSResponse<Void>() {
        };
    }
}
