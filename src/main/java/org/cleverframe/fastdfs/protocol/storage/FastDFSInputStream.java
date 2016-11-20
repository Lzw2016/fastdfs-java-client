package org.cleverframe.fastdfs.protocol.storage;

import java.io.IOException;
import java.io.InputStream;

/**
 * 封装FastDFS数据流
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 16:07 <br/>
 */
public class FastDFSInputStream extends InputStream {
    private final InputStream inputStream;
    private final long size;
    private long remainByteSize;

    public FastDFSInputStream(InputStream ins, long size) {
        this.inputStream = ins;
        this.size = size;
        remainByteSize = size;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        if (remainByteSize == 0) {
            return -1;
        }
        int byteSize = inputStream.read(b, off, len);
        if (remainByteSize < byteSize) {
            throw new IOException("协议长度" + size + "与实际长度不符");
        }
        remainByteSize -= byteSize;
        return byteSize;
    }

    @Override
    public void close() throws IOException {
    }

    /**
     * 是否已完成读取
     */
    public boolean isReadCompleted() {
        return remainByteSize == 0;
    }
}
