package org.cleverframe.fastdfs.model;

import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.mapper.FastDFSColumn;

import java.io.Serializable;

/**
 * FastDFS中group的状态信息
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:10 <br/>
 */
public class GroupState implements Serializable {
    /**
     * group名称
     */
    @FastDFSColumn(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN + 1)
    private String groupName;

    /**
     * group总计存储容量(MB)
     */
    @FastDFSColumn(index = 1)
    private long totalMB;

    /**
     * group空闲存储容量(MB)
     */
    @FastDFSColumn(index = 2)
    private long freeMB;

    /**
     * trunk free space in MB
     */
    @FastDFSColumn(index = 3)
    private long trunkFreeMB;

    /**
     * storage server count
     */
    @FastDFSColumn(index = 4)
    private int storageCount;

    /**
     * storage server port
     */
    @FastDFSColumn(index = 5)
    private int storagePort;

    /**
     * storage server HTTP port
     */
    @FastDFSColumn(index = 6)
    private int storageHttpPort;

    /**
     * active storage server count
     */
    @FastDFSColumn(index = 7)
    private int activeCount;

    /**
     * current storage server index to upload file
     */
    @FastDFSColumn(index = 8)
    private int currentWriteServer;

    /**
     * store base path count of each storage server
     */
    @FastDFSColumn(index = 9)
    private int storePathCount;

    /**
     * sub dir count per store path
     */
    @FastDFSColumn(index = 10)
    private int subdirCountPerPath;

    /**
     * current trunk file id
     */
    @FastDFSColumn(index = 11)
    private int currentTrunkFileId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getTotalMB() {
        return totalMB;
    }

    public void setTotalMB(long totalMB) {
        this.totalMB = totalMB;
    }

    public long getFreeMB() {
        return freeMB;
    }

    public void setFreeMB(long freeMB) {
        this.freeMB = freeMB;
    }

    public long getTrunkFreeMB() {
        return trunkFreeMB;
    }

    public void setTrunkFreeMB(long trunkFreeMB) {
        this.trunkFreeMB = trunkFreeMB;
    }

    public int getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(int storageCount) {
        this.storageCount = storageCount;
    }

    public int getStoragePort() {
        return storagePort;
    }

    public void setStoragePort(int storagePort) {
        this.storagePort = storagePort;
    }

    public int getStorageHttpPort() {
        return storageHttpPort;
    }

    public void setStorageHttpPort(int storageHttpPort) {
        this.storageHttpPort = storageHttpPort;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getCurrentWriteServer() {
        return currentWriteServer;
    }

    public void setCurrentWriteServer(int currentWriteServer) {
        this.currentWriteServer = currentWriteServer;
    }

    public int getStorePathCount() {
        return storePathCount;
    }

    public void setStorePathCount(int storePathCount) {
        this.storePathCount = storePathCount;
    }

    public int getSubdirCountPerPath() {
        return subdirCountPerPath;
    }

    public void setSubdirCountPerPath(int subdirCountPerPath) {
        this.subdirCountPerPath = subdirCountPerPath;
    }

    public int getCurrentTrunkFileId() {
        return currentTrunkFileId;
    }

    public void setCurrentTrunkFileId(int currentTrunkFileId) {
        this.currentTrunkFileId = currentTrunkFileId;
    }

    @Override
    public String toString() {
        return "GroupState{" +
                "groupName='" + groupName + '\'' +
                ", totalMB=" + totalMB +
                ", freeMB=" + freeMB +
                ", trunkFreeMB=" + trunkFreeMB +
                ", storageCount=" + storageCount +
                ", storagePort=" + storagePort +
                ", storageHttpPort=" + storageHttpPort +
                ", activeCount=" + activeCount +
                ", currentWriteServer=" + currentWriteServer +
                ", storePathCount=" + storePathCount +
                ", subdirCountPerPath=" + subdirCountPerPath +
                ", currentTrunkFileId=" + currentTrunkFileId +
                '}';
    }
}
