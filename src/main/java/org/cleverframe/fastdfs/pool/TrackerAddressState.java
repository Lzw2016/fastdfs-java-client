package org.cleverframe.fastdfs.pool;

import java.net.InetSocketAddress;

/**
 * 管理TrackerAddress当前状态
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 20:00 <br/>
 */
public class TrackerAddressState {

    /**
     * 连接地址
     */
    private InetSocketAddress address;

    /**
     * 当前是否有效 默认有效
     */
    private boolean available = true;

    /**
     * 上次无效时间
     */
    private long lastUnavailableTime;

    /**
     * 构造函数
     *
     * @param address 连接地址
     */
    public TrackerAddressState(InetSocketAddress address) {
        this.address = address;
        this.available = true;
    }

    /**
     * 是否可以尝试连接<br/>
     * 距离最后不可用时间n秒后重试
     *
     * @param retryAfterSecond n秒后可以重试
     */
    public boolean canTryToConnect(int retryAfterSecond) {
        return available || (System.currentTimeMillis() - lastUnavailableTime) > (retryAfterSecond * 1000);
    }

    /**
     * 设置连接是否有效
     *
     * @param available true有效，false无效
     */
    public void setAvailable(boolean available) {
        this.available = available;
        if (!available) {
            this.lastUnavailableTime = System.currentTimeMillis();
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public long getLastUnavailableTime() {
        return lastUnavailableTime;
    }

    @Override
    public String toString() {
        return "TrackerAddressState{" +
                "address=" + address +
                ", available=" + available +
                ", lastUnavailableTime=" + lastUnavailableTime +
                '}';
    }
}
