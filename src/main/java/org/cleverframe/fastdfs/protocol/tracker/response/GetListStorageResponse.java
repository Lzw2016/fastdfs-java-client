package org.cleverframe.fastdfs.protocol.tracker.response;

import org.cleverframe.fastdfs.model.StorageState;
import org.cleverframe.fastdfs.protocol.FastDFSResponse;
import org.cleverframe.fastdfs.protocol.mapper.ObjectMateData;
import org.cleverframe.fastdfs.utils.FastDFSParamMapperUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取Storage服务器状态响应
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:48 <br/>
 */
public class GetListStorageResponse extends FastDFSResponse<List<StorageState>> {

    /**
     * 解析Group
     */
//    @Override
    public List<StorageState> decode(byte[] bs, Charset charset) throws IOException {
        // 获取对象转换定义
        ObjectMateData objectMateData = FastDFSParamMapperUtils.getObjectMap(StorageState.class);
        int fixFieldsTotalSize = objectMateData.getFieldsFixTotalSize();
        if (bs.length % fixFieldsTotalSize != 0) {
            throw new IOException("fixFieldsTotalSize=" + fixFieldsTotalSize + "but byte array length: " + bs.length + " is invalid!");
        }
        // 计算反馈对象数量
        int count = bs.length / fixFieldsTotalSize;
        int offset = 0;
        List<StorageState> results = new ArrayList<StorageState>(count);
        for (int i = 0; i < count; i++) {
            byte[] one = new byte[fixFieldsTotalSize];
            System.arraycopy(bs, offset, one, 0, fixFieldsTotalSize);
            results.add(FastDFSParamMapperUtils.map(one, StorageState.class, charset));
            offset += fixFieldsTotalSize;
        }
        return results;
    }

    /**
     * 解析反馈内容
     */
    @Override
    public List<StorageState> decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        byte[] bytes = new byte[(int) getContentLength()];
        int contentSize = in.read(bytes);
        if (contentSize != getContentLength()) {
            throw new IOException("读取到的数据长度与协议长度不符");
        }
        return decode(bytes, charset);
    }
}
