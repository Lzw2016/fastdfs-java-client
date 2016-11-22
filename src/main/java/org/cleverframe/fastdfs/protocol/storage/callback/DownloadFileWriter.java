package org.cleverframe.fastdfs.protocol.storage.callback;


import org.cleverframe.fastdfs.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 直接把文件下载到本地文件系统
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:57 <br/>
 */
public class DownloadFileWriter implements DownloadCallback<String> {

    /**
     * 文件名称
     */
    private String fileName;

    public DownloadFileWriter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 文件下载处理
     *
     * @return 返回文件名称
     */
    @Override
    public String receive(InputStream inputStream) throws IOException {
        FileOutputStream out = null;
        InputStream in = null;
        try {
            out = new FileOutputStream(fileName);
            in = new BufferedInputStream(inputStream);
            // 通过ioutil 对接输入输出流，实现文件下载
            IOUtils.copy(in, out);
            out.flush();
        } finally {
            // 关闭流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return fileName;
    }
}