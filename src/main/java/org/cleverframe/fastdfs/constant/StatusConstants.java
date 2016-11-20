package org.cleverframe.fastdfs.constant;

/**
 * FastDFS协议服务端状态的常量
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:10 <br/>
 */
public final class StatusConstants {
    public static final byte FDFS_STORAGE_STATUS_INIT = 0;
    public static final byte FDFS_STORAGE_STATUS_WAIT_SYNC = 1;
    public static final byte FDFS_STORAGE_STATUS_SYNCING = 2;
    public static final byte FDFS_STORAGE_STATUS_IP_CHANGED = 3;
    public static final byte FDFS_STORAGE_STATUS_DELETED = 4;
    public static final byte FDFS_STORAGE_STATUS_OFFLINE = 5;
    public static final byte FDFS_STORAGE_STATUS_ONLINE = 6;
    public static final byte FDFS_STORAGE_STATUS_ACTIVE = 7;
    public static final byte FDFS_STORAGE_STATUS_NONE = 99;
}
