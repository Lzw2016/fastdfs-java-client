package org.cleverframe.fastdfs.model;

import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.protocol.mapper.FastDFSColumn;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 * 向tracker请求上传、下载文件或其他文件请求时，tracker返回的文件storage节点的信息
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:21 <br/>
 */
public class StorageNodeInfo implements Serializable {
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    @FastDFSColumn(index = 1, max = OtherConstants.FDFS_IPADDR_SIZE - 1)
    private String ip;

    @FastDFSColumn(index = 2)
    private int port;

    /**
     * 存储节点
     *
     * @param ip   存储服务器IP地址
     * @param port 存储服务器端口号
     */
    public StorageNodeInfo(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    public StorageNodeInfo() {
        super();
    }

    /**
     * @return 根据IP和端口 返回 InetSocketAddress 对象
     */
    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(ip, port);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "StorageNodeInfo{" +
                "groupName='" + groupName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
