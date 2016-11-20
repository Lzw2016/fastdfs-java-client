package org.cleverframe.fastdfs.protocol.storage.response;

import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.protocol.BaseResponse;
import org.cleverframe.fastdfs.utils.MetadataMapperUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 17:10 <br/>
 */
public class GetMetadataResponse extends BaseResponse<Set<MateData>> {

    /**
     * 解析反馈内容
     */
    @Override
    public Set<MateData> decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        byte[] bytes = new byte[(int) getContentLength()];
        int contentSize = in.read(bytes);
        if (contentSize != getContentLength()) {
            throw new IOException("读取到的数据长度与协议长度不符");
        }
        return MetadataMapperUtils.fromByte(bytes, charset);
    }
}
