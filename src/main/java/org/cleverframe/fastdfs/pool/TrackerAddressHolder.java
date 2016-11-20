package org.cleverframe.fastdfs.pool;

import java.net.InetSocketAddress;

/**
 * 管理TrackerAddress当前状态
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 20:00 <br/>
 */
public class TrackerAddressHolder {
    /**
     * 连接地址
     */
    private InetSocketAddress address;
    /**
     * 当前是否有效
     */
    private boolean available;
    /**
     * 上次无效时间
     */
    private long lastUnavailableTime;

    /**
     * 构造函数
     *
     * @param address 连接地址
     */
    public TrackerAddressHolder(InetSocketAddress address) {
        super();
        this.address = address;
        // 默认Tracker有效
        this.available = true;
    }

    /**
     * 设置连接有效
     */
    public void setActive() {
        this.available = true;
    }

    /**
     * 设置连接无效
     */
    public void setInActive() {
        this.available = false;
        this.lastUnavailableTime = System.currentTimeMillis();
    }

    /**
     * 是否可以尝试连接
     *
     * @param retryAfterSecend 在n秒后重试
     */
    public boolean canTryToConnect(int retryAfterSecend) {
        // 如果是有效连接
        if (this.available) {
            return true;
            // 如果连接无效，并且达到重试时间
        } else if ((System.currentTimeMillis() - lastUnavailableTime) > retryAfterSecend * 1000) {
            return true;
        }
        return false;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public boolean isAvailable() {
        return available;
    }

    public long getLastUnavailableTime() {
        return lastUnavailableTime;
    }
}
