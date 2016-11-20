package org.cleverframe.fastdfs.protocol.storage.callback;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件下载回调接口
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:53 <br/>
 */
public interface DownloadCallback<T> {

    /**
     * 注意不能直接返回入参的InputStream，因为此方法返回后将关闭原输入流<br/>
     * 不能关闭inputStream? TODO 验证是否可以关闭
     *
     * @param inputStream 返回数据输入流
     */
    T receive(InputStream inputStream) throws IOException;
}
