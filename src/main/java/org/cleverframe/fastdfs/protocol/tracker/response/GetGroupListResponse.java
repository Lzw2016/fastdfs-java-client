package org.cleverframe.fastdfs.protocol.tracker.response;

import org.cleverframe.fastdfs.model.GroupState;
import org.cleverframe.fastdfs.protocol.BaseResponse;
import org.cleverframe.fastdfs.mapper.ObjectMateData;
import org.cleverframe.fastdfs.utils.FastDfsParamMapperUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取Group信息相应
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:09 <br/>
 */
public class GetGroupListResponse extends BaseResponse<List<GroupState>> {

    /**
     * 解析反馈内容
     */
    @Override
    public List<GroupState> decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        byte[] bytes = new byte[(int) getContentLength()];
        int contentSize = in.read(bytes);
        // 此处fastdfs的服务端有bug
        if (contentSize != getContentLength()) {
            try {
                return decodeGroup(bytes, charset);
            } catch (Exception e) {
                throw new IOException("读取到的数据长度与协议长度不符");
            }
        } else {
            return decodeGroup(bytes, charset);
        }
    }

    /**
     * 解析Group
     */
    private List<GroupState> decodeGroup(byte[] bs, Charset charset) throws IOException {
        // 获取对象转换定义
        ObjectMateData objectMateData = FastDfsParamMapperUtils.getObjectMap(GroupState.class);
        int fixFieldsTotalSize = objectMateData.getFieldsFixTotalSize();
        if (bs.length % fixFieldsTotalSize != 0) {
            throw new IOException("FixFieldsTotalSize=" + fixFieldsTotalSize + ", 但是数据长度=" + bs.length + ", 数据无效");
        }
        // 计算反馈对象数量
        int count = bs.length / fixFieldsTotalSize;
        int offset = 0;
        List<GroupState> results = new ArrayList<GroupState>(count);
        for (int i = 0; i < count; i++) {
            byte[] one = new byte[fixFieldsTotalSize];
            System.arraycopy(bs, offset, one, 0, fixFieldsTotalSize);
            results.add(FastDfsParamMapperUtils.map(one, GroupState.class, charset));
            offset += fixFieldsTotalSize;
        }
        return results;
    }
}