package org.cleverframe.fastdfs.model;

import org.cleverframe.fastdfs.constant.OtherConstants;
import org.cleverframe.fastdfs.mapper.FastDFSColumn;

import java.io.Serializable;
import java.util.Date;

/**
 * FastDFS中storage节点的状态信息
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 11:24 <br/>
 */
public class StorageState implements Serializable {

    /**
     * 状态代码
     */
    @FastDFSColumn(index = 0)
    private byte status;

    /**
     * id
     */
    @FastDFSColumn(index = 1, max = OtherConstants.FDFS_STORAGE_ID_MAX_SIZE)
    private String id;

    /**
     * ip地址
     */
    @FastDFSColumn(index = 2, max = OtherConstants.FDFS_IPADDR_SIZE)
    private String ipAddr;

    /**
     * domain
     */
    @FastDFSColumn(index = 3, max = OtherConstants.FDFS_DOMAIN_NAME_MAX_SIZE)
    private String domainName; // http domain name

    /**
     * 源ip地址
     */
    @FastDFSColumn(index = 4, max = OtherConstants.FDFS_IPADDR_SIZE)
    private String srcIpAddr;

    /**
     * version
     */
    @FastDFSColumn(index = 5, max = OtherConstants.FDFS_VERSION_SIZE)
    private String version;

    /**
     * 存储加入时间
     */
    @FastDFSColumn(index = 6)
    private Date joinTime; // storage join timestamp (create timestamp)

    /**
     * 存储更新时间
     */
    @FastDFSColumn(index = 7)
    private Date upTime; // storage service started timestamp

    /**
     * 存储总容量
     */
    @FastDFSColumn(index = 8)
    private long totalMB; // total disk storage in MB

    /**
     * 空闲存储
     */
    @FastDFSColumn(index = 9)
    private long freeMB; // free disk storage in MB

    /**
     * 文件上传权重
     */
    @FastDFSColumn(index = 10)
    private int uploadPriority; // upload priority

    /**
     * 存储路径数
     */
    @FastDFSColumn(index = 11)
    private int storePathCount; // store base path count of each storage

    // server
    /**
     * 存储路径子目录数
     */
    @FastDFSColumn(index = 12)
    private int subdirCountPerPath;

    /**
     * 当前写路径
     */
    @FastDFSColumn(index = 13)
    private int currentWritePath; // current write path index

    /**
     * 存储端口
     */
    @FastDFSColumn(index = 14)
    private int storagePort;

    /**
     * 存储http端口
     */
    @FastDFSColumn(index = 15)
    private int storageHttpPort; // storage http server port

    @FastDFSColumn(index = 16, max = OtherConstants.FDFS_PROTO_CONNECTION_LEN)
    private int connectionAllocCount;

    @FastDFSColumn(index = 17, max = OtherConstants.FDFS_PROTO_CONNECTION_LEN)
    private int connectionCurrentCount;

    @FastDFSColumn(index = 18, max = OtherConstants.FDFS_PROTO_CONNECTION_LEN)
    private int connectionMaxCount;

    /**
     * 总上传文件数
     */
    @FastDFSColumn(index = 19)
    private long totalUploadCount;

    /**
     * 成功上传文件数
     */
    @FastDFSColumn(index = 20)
    private long successUploadCount;

    /**
     * 合并存储文件数
     */
    @FastDFSColumn(index = 21)
    private long totalAppendCount;

    /**
     * 成功合并文件数
     */
    @FastDFSColumn(index = 22)
    private long successAppendCount;

    /**
     * 文件修改数
     */
    @FastDFSColumn(index = 23)
    private long totalModifyCount;

    /**
     * 文件成功修改数
     */
    @FastDFSColumn(index = 24)
    private long successModifyCount;

    /**
     * 总清除数
     */
    @FastDFSColumn(index = 25)
    private long totalTruncateCount;

    /**
     * 成功清除数
     */
    @FastDFSColumn(index = 26)
    private long successTruncateCount;

    /**
     * 总设置标签数
     */
    @FastDFSColumn(index = 27)
    private long totalSetMetaCount;

    /**
     * 成功设置标签数
     */
    @FastDFSColumn(index = 28)
    private long successSetMetaCount;

    /**
     * 总删除文件数
     */
    @FastDFSColumn(index = 29)
    private long totalDeleteCount;

    /**
     * 成功删除文件数
     */
    @FastDFSColumn(index = 30)
    private long successDeleteCount;

    /**
     * 总下载量
     */
    @FastDFSColumn(index = 31)
    private long totalDownloadCount;

    /**
     * 成功下载量
     */
    @FastDFSColumn(index = 32)
    private long successDownloadCount;

    /**
     * 总获取标签数
     */
    @FastDFSColumn(index = 33)
    private long totalGetMetaCount;

    /**
     * 成功获取标签数
     */
    @FastDFSColumn(index = 34)
    private long successGetMetaCount;

    /**
     * 总创建链接数
     */
    @FastDFSColumn(index = 35)
    private long totalCreateLinkCount;

    /**
     * 成功创建链接数
     */
    @FastDFSColumn(index = 36)
    private long successCreateLinkCount;

    /**
     * 总删除链接数
     */
    @FastDFSColumn(index = 37)
    private long totalDeleteLinkCount;

    /**
     * 成功删除链接数
     */
    @FastDFSColumn(index = 38)
    private long successDeleteLinkCount;

    /**
     * 总上传数据量
     */
    @FastDFSColumn(index = 39)
    private long totalUploadBytes;

    /**
     * 成功上传数据量
     */
    @FastDFSColumn(index = 40)
    private long successUploadBytes;

    /**
     * 合并数据量
     */
    @FastDFSColumn(index = 41)
    private long totalAppendBytes;

    /**
     * 成功合并数据量
     */
    @FastDFSColumn(index = 42)
    private long successAppendBytes;

    /**
     * 修改数据量
     */
    @FastDFSColumn(index = 43)
    private long totalModifyBytes;

    /**
     * 成功修改数据量
     */
    @FastDFSColumn(index = 44)
    private long successModifyBytes;

    /**
     * 下载数据量
     */
    @FastDFSColumn(index = 45)
    private long totalDownloadloadBytes;

    /**
     * 成功下载数据量
     */
    @FastDFSColumn(index = 46)
    private long successDownloadloadBytes;

    /**
     * 同步数据量
     */
    @FastDFSColumn(index = 47)
    private long totalSyncInBytes;

    /**
     * 成功同步数据量
     */
    @FastDFSColumn(index = 48)
    private long successSyncInBytes;

    /**
     * 同步输出数据量
     */
    @FastDFSColumn(index = 49)
    private long totalSyncOutBytes;

    /**
     * 成功同步输出数据量
     */
    @FastDFSColumn(index = 50)
    private long successSyncOutBytes;

    /**
     * 打开文件数量
     */
    @FastDFSColumn(index = 51)
    private long totalFileOpenCount;

    /**
     * 成功打开文件数量
     */
    @FastDFSColumn(index = 52)
    private long successFileOpenCount;

    /**
     * 文件读取数量
     */
    @FastDFSColumn(index = 53)
    private long totalFileReadCount;

    /**
     * 文件成功读取数量
     */
    @FastDFSColumn(index = 54)
    private long successFileReadCount;

    /**
     * 文件写数量
     */
    @FastDFSColumn(index = 56)
    private long totalFileWriteCount;

    /**
     * 文件成功写数量
     */
    @FastDFSColumn(index = 57)
    private long successFileWriteCount;

    /**
     * 最后上传时间
     */
    @FastDFSColumn(index = 58)
    private Date lastSourceUpdate;

    /**
     * 最后同步时间
     */
    @FastDFSColumn(index = 59)
    private Date lastSyncUpdate;

    /**
     * 最后同步时间戳
     */
    @FastDFSColumn(index = 60)
    private Date lastSyncedTimestamp;

    /**
     * 最后心跳时间
     */
    @FastDFSColumn(index = 61)
    private Date lastHeartBeatTime;

    /**
     * 是否trunk服务器
     */
    @FastDFSColumn(index = 62)
    private boolean isTrunkServer;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getSrcIpAddr() {
        return srcIpAddr;
    }

    public void setSrcIpAddr(String srcIpAddr) {
        this.srcIpAddr = srcIpAddr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
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

    public int getUploadPriority() {
        return uploadPriority;
    }

    public void setUploadPriority(int uploadPriority) {
        this.uploadPriority = uploadPriority;
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

    public int getCurrentWritePath() {
        return currentWritePath;
    }

    public void setCurrentWritePath(int currentWritePath) {
        this.currentWritePath = currentWritePath;
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

    public int getConnectionAllocCount() {
        return connectionAllocCount;
    }

    public void setConnectionAllocCount(int connectionAllocCount) {
        this.connectionAllocCount = connectionAllocCount;
    }

    public int getConnectionCurrentCount() {
        return connectionCurrentCount;
    }

    public void setConnectionCurrentCount(int connectionCurrentCount) {
        this.connectionCurrentCount = connectionCurrentCount;
    }

    public int getConnectionMaxCount() {
        return connectionMaxCount;
    }

    public void setConnectionMaxCount(int connectionMaxCount) {
        this.connectionMaxCount = connectionMaxCount;
    }

    public long getTotalUploadCount() {
        return totalUploadCount;
    }

    public void setTotalUploadCount(long totalUploadCount) {
        this.totalUploadCount = totalUploadCount;
    }

    public long getSuccessUploadCount() {
        return successUploadCount;
    }

    public void setSuccessUploadCount(long successUploadCount) {
        this.successUploadCount = successUploadCount;
    }

    public long getTotalAppendCount() {
        return totalAppendCount;
    }

    public void setTotalAppendCount(long totalAppendCount) {
        this.totalAppendCount = totalAppendCount;
    }

    public long getSuccessAppendCount() {
        return successAppendCount;
    }

    public void setSuccessAppendCount(long successAppendCount) {
        this.successAppendCount = successAppendCount;
    }

    public long getTotalModifyCount() {
        return totalModifyCount;
    }

    public void setTotalModifyCount(long totalModifyCount) {
        this.totalModifyCount = totalModifyCount;
    }

    public long getSuccessModifyCount() {
        return successModifyCount;
    }

    public void setSuccessModifyCount(long successModifyCount) {
        this.successModifyCount = successModifyCount;
    }

    public long getTotalTruncateCount() {
        return totalTruncateCount;
    }

    public void setTotalTruncateCount(long totalTruncateCount) {
        this.totalTruncateCount = totalTruncateCount;
    }

    public long getSuccessTruncateCount() {
        return successTruncateCount;
    }

    public void setSuccessTruncateCount(long successTruncateCount) {
        this.successTruncateCount = successTruncateCount;
    }

    public long getTotalSetMetaCount() {
        return totalSetMetaCount;
    }

    public void setTotalSetMetaCount(long totalSetMetaCount) {
        this.totalSetMetaCount = totalSetMetaCount;
    }

    public long getSuccessSetMetaCount() {
        return successSetMetaCount;
    }

    public void setSuccessSetMetaCount(long successSetMetaCount) {
        this.successSetMetaCount = successSetMetaCount;
    }

    public long getTotalDeleteCount() {
        return totalDeleteCount;
    }

    public void setTotalDeleteCount(long totalDeleteCount) {
        this.totalDeleteCount = totalDeleteCount;
    }

    public long getSuccessDeleteCount() {
        return successDeleteCount;
    }

    public void setSuccessDeleteCount(long successDeleteCount) {
        this.successDeleteCount = successDeleteCount;
    }

    public long getTotalDownloadCount() {
        return totalDownloadCount;
    }

    public void setTotalDownloadCount(long totalDownloadCount) {
        this.totalDownloadCount = totalDownloadCount;
    }

    public long getSuccessDownloadCount() {
        return successDownloadCount;
    }

    public void setSuccessDownloadCount(long successDownloadCount) {
        this.successDownloadCount = successDownloadCount;
    }

    public long getTotalGetMetaCount() {
        return totalGetMetaCount;
    }

    public void setTotalGetMetaCount(long totalGetMetaCount) {
        this.totalGetMetaCount = totalGetMetaCount;
    }

    public long getSuccessGetMetaCount() {
        return successGetMetaCount;
    }

    public void setSuccessGetMetaCount(long successGetMetaCount) {
        this.successGetMetaCount = successGetMetaCount;
    }

    public long getTotalCreateLinkCount() {
        return totalCreateLinkCount;
    }

    public void setTotalCreateLinkCount(long totalCreateLinkCount) {
        this.totalCreateLinkCount = totalCreateLinkCount;
    }

    public long getSuccessCreateLinkCount() {
        return successCreateLinkCount;
    }

    public void setSuccessCreateLinkCount(long successCreateLinkCount) {
        this.successCreateLinkCount = successCreateLinkCount;
    }

    public long getTotalDeleteLinkCount() {
        return totalDeleteLinkCount;
    }

    public void setTotalDeleteLinkCount(long totalDeleteLinkCount) {
        this.totalDeleteLinkCount = totalDeleteLinkCount;
    }

    public long getSuccessDeleteLinkCount() {
        return successDeleteLinkCount;
    }

    public void setSuccessDeleteLinkCount(long successDeleteLinkCount) {
        this.successDeleteLinkCount = successDeleteLinkCount;
    }

    public long getTotalUploadBytes() {
        return totalUploadBytes;
    }

    public void setTotalUploadBytes(long totalUploadBytes) {
        this.totalUploadBytes = totalUploadBytes;
    }

    public long getSuccessUploadBytes() {
        return successUploadBytes;
    }

    public void setSuccessUploadBytes(long successUploadBytes) {
        this.successUploadBytes = successUploadBytes;
    }

    public long getTotalAppendBytes() {
        return totalAppendBytes;
    }

    public void setTotalAppendBytes(long totalAppendBytes) {
        this.totalAppendBytes = totalAppendBytes;
    }

    public long getSuccessAppendBytes() {
        return successAppendBytes;
    }

    public void setSuccessAppendBytes(long successAppendBytes) {
        this.successAppendBytes = successAppendBytes;
    }

    public long getTotalModifyBytes() {
        return totalModifyBytes;
    }

    public void setTotalModifyBytes(long totalModifyBytes) {
        this.totalModifyBytes = totalModifyBytes;
    }

    public long getSuccessModifyBytes() {
        return successModifyBytes;
    }

    public void setSuccessModifyBytes(long successModifyBytes) {
        this.successModifyBytes = successModifyBytes;
    }

    public long getTotalDownloadloadBytes() {
        return totalDownloadloadBytes;
    }

    public void setTotalDownloadloadBytes(long totalDownloadloadBytes) {
        this.totalDownloadloadBytes = totalDownloadloadBytes;
    }

    public long getSuccessDownloadloadBytes() {
        return successDownloadloadBytes;
    }

    public void setSuccessDownloadloadBytes(long successDownloadloadBytes) {
        this.successDownloadloadBytes = successDownloadloadBytes;
    }

    public long getTotalSyncInBytes() {
        return totalSyncInBytes;
    }

    public void setTotalSyncInBytes(long totalSyncInBytes) {
        this.totalSyncInBytes = totalSyncInBytes;
    }

    public long getSuccessSyncInBytes() {
        return successSyncInBytes;
    }

    public void setSuccessSyncInBytes(long successSyncInBytes) {
        this.successSyncInBytes = successSyncInBytes;
    }

    public long getTotalSyncOutBytes() {
        return totalSyncOutBytes;
    }

    public void setTotalSyncOutBytes(long totalSyncOutBytes) {
        this.totalSyncOutBytes = totalSyncOutBytes;
    }

    public long getSuccessSyncOutBytes() {
        return successSyncOutBytes;
    }

    public void setSuccessSyncOutBytes(long successSyncOutBytes) {
        this.successSyncOutBytes = successSyncOutBytes;
    }

    public long getTotalFileOpenCount() {
        return totalFileOpenCount;
    }

    public void setTotalFileOpenCount(long totalFileOpenCount) {
        this.totalFileOpenCount = totalFileOpenCount;
    }

    public long getSuccessFileOpenCount() {
        return successFileOpenCount;
    }

    public void setSuccessFileOpenCount(long successFileOpenCount) {
        this.successFileOpenCount = successFileOpenCount;
    }

    public long getTotalFileReadCount() {
        return totalFileReadCount;
    }

    public void setTotalFileReadCount(long totalFileReadCount) {
        this.totalFileReadCount = totalFileReadCount;
    }

    public long getSuccessFileReadCount() {
        return successFileReadCount;
    }

    public void setSuccessFileReadCount(long successFileReadCount) {
        this.successFileReadCount = successFileReadCount;
    }

    public long getTotalFileWriteCount() {
        return totalFileWriteCount;
    }

    public void setTotalFileWriteCount(long totalFileWriteCount) {
        this.totalFileWriteCount = totalFileWriteCount;
    }

    public long getSuccessFileWriteCount() {
        return successFileWriteCount;
    }

    public void setSuccessFileWriteCount(long successFileWriteCount) {
        this.successFileWriteCount = successFileWriteCount;
    }

    public Date getLastSourceUpdate() {
        return lastSourceUpdate;
    }

    public void setLastSourceUpdate(Date lastSourceUpdate) {
        this.lastSourceUpdate = lastSourceUpdate;
    }

    public Date getLastSyncUpdate() {
        return lastSyncUpdate;
    }

    public void setLastSyncUpdate(Date lastSyncUpdate) {
        this.lastSyncUpdate = lastSyncUpdate;
    }

    public Date getLastSyncedTimestamp() {
        return lastSyncedTimestamp;
    }

    public void setLastSyncedTimestamp(Date lastSyncedTimestamp) {
        this.lastSyncedTimestamp = lastSyncedTimestamp;
    }

    public Date getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(Date lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public boolean isTrunkServer() {
        return isTrunkServer;
    }

    public void setTrunkServer(boolean trunkServer) {
        isTrunkServer = trunkServer;
    }

    @Override
    public String toString() {
        return "StorageState{" +
                "status=" + status +
                ", id='" + id + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", domainName='" + domainName + '\'' +
                ", srcIpAddr='" + srcIpAddr + '\'' +
                ", version='" + version + '\'' +
                ", joinTime=" + joinTime +
                ", upTime=" + upTime +
                ", totalMB=" + totalMB +
                ", freeMB=" + freeMB +
                ", uploadPriority=" + uploadPriority +
                ", storePathCount=" + storePathCount +
                ", subdirCountPerPath=" + subdirCountPerPath +
                ", currentWritePath=" + currentWritePath +
                ", storagePort=" + storagePort +
                ", storageHttpPort=" + storageHttpPort +
                ", connectionAllocCount=" + connectionAllocCount +
                ", connectionCurrentCount=" + connectionCurrentCount +
                ", connectionMaxCount=" + connectionMaxCount +
                ", totalUploadCount=" + totalUploadCount +
                ", successUploadCount=" + successUploadCount +
                ", totalAppendCount=" + totalAppendCount +
                ", successAppendCount=" + successAppendCount +
                ", totalModifyCount=" + totalModifyCount +
                ", successModifyCount=" + successModifyCount +
                ", totalTruncateCount=" + totalTruncateCount +
                ", successTruncateCount=" + successTruncateCount +
                ", totalSetMetaCount=" + totalSetMetaCount +
                ", successSetMetaCount=" + successSetMetaCount +
                ", totalDeleteCount=" + totalDeleteCount +
                ", successDeleteCount=" + successDeleteCount +
                ", totalDownloadCount=" + totalDownloadCount +
                ", successDownloadCount=" + successDownloadCount +
                ", totalGetMetaCount=" + totalGetMetaCount +
                ", successGetMetaCount=" + successGetMetaCount +
                ", totalCreateLinkCount=" + totalCreateLinkCount +
                ", successCreateLinkCount=" + successCreateLinkCount +
                ", totalDeleteLinkCount=" + totalDeleteLinkCount +
                ", successDeleteLinkCount=" + successDeleteLinkCount +
                ", totalUploadBytes=" + totalUploadBytes +
                ", successUploadBytes=" + successUploadBytes +
                ", totalAppendBytes=" + totalAppendBytes +
                ", successAppendBytes=" + successAppendBytes +
                ", totalModifyBytes=" + totalModifyBytes +
                ", successModifyBytes=" + successModifyBytes +
                ", totalDownloadloadBytes=" + totalDownloadloadBytes +
                ", successDownloadloadBytes=" + successDownloadloadBytes +
                ", totalSyncInBytes=" + totalSyncInBytes +
                ", successSyncInBytes=" + successSyncInBytes +
                ", totalSyncOutBytes=" + totalSyncOutBytes +
                ", successSyncOutBytes=" + successSyncOutBytes +
                ", totalFileOpenCount=" + totalFileOpenCount +
                ", successFileOpenCount=" + successFileOpenCount +
                ", totalFileReadCount=" + totalFileReadCount +
                ", successFileReadCount=" + successFileReadCount +
                ", totalFileWriteCount=" + totalFileWriteCount +
                ", successFileWriteCount=" + successFileWriteCount +
                ", lastSourceUpdate=" + lastSourceUpdate +
                ", lastSyncUpdate=" + lastSyncUpdate +
                ", lastSyncedTimestamp=" + lastSyncedTimestamp +
                ", lastHeartBeatTime=" + lastHeartBeatTime +
                ", isTrunkServer=" + isTrunkServer +
                '}';
    }
}
