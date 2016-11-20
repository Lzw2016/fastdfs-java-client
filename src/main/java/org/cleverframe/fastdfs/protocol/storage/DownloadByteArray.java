package org.cleverframe.fastdfs.protocol.storage;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 直接返回Byte[]数据
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:56 <br/>
 */
public class DownloadByteArray implements DownloadCallback<byte[]> {
    @Override
    public byte[] receive(InputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }
}