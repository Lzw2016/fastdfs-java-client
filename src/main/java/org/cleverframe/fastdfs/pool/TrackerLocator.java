package org.cleverframe.fastdfs.pool;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.fastdfs.exception.FastDfsUnavailableException;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * 表示Tracker服务器位置<br/>
 * 支持负载均衡对IP轮询<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:58 <br/>
 */
public class TrackerLocator {
    /**
     * 10分钟以后重试连接
     */
    private static final int DEFAULT_RETRY_AFTER_SECEND = 10 * 60;

    /**
     * 连接中断以后经过N秒重试
     */
    private int retryAfterSecend = DEFAULT_RETRY_AFTER_SECEND;

    /**
     * tracker服务配置地址列表
     */
    private List<String> trackerList = new ArrayList<String>();

    /**
     * 目录服务地址-为了加速处理，增加了一个map
     */
    private Map<InetSocketAddress, TrackerAddressHolder> trackerAddressMap = new HashMap<InetSocketAddress, TrackerAddressHolder>();

    /**
     * 轮询圈
     */
    private CircularList<TrackerAddressHolder> trackerAddressCircular = new CircularList<TrackerAddressHolder>();

    /**
     * 初始化Tracker服务器地址
     * 配置方式为 ip:port 如 192.168.1.2:21000
     */
    public TrackerLocator(List<String> trackerList) {
        super();
        this.trackerList = trackerList;
        buildTrackerAddresses();
    }

    /**
     * 分析TrackerAddress
     */
    private void buildTrackerAddresses() {
        Set<InetSocketAddress> addressSet = new HashSet<InetSocketAddress>();
        for (String item : trackerList) {
            if (StringUtils.isBlank(item)) {
                continue;
            }
            String[] parts = StringUtils.split(item, ":", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Tracker Server地址[" + item + "]格式无效，正确格式 host:port");
            }
            InetSocketAddress address;
            try {
                address = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            } catch (Throwable e) {
                throw new IllegalArgumentException("Tracker Server地址[" + item + "]格式无效，正确格式 host:port", e);
            }
            addressSet.add(address);
        }
        // 放到轮询圈
        for (InetSocketAddress item : addressSet) {
            TrackerAddressHolder holder = new TrackerAddressHolder(item);
            trackerAddressCircular.add(holder);
            trackerAddressMap.put(item, holder);
        }
    }

    /**
     * 获取Tracker服务器地址
     */
    public InetSocketAddress getTrackerAddress() {
        TrackerAddressHolder holder;
        // 遍历连接地址,抓取当前有效的地址
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            holder = trackerAddressCircular.next();
            if (holder.canTryToConnect(retryAfterSecend)) {
                return holder.getAddress();
            }
        }
        throw new FastDfsUnavailableException("找不到可用的 Tracker Server " + getTrackerAddressConfigString());
    }

    /**
     * 获取配置地址列表
     */
    private String getTrackerAddressConfigString() {
        StringBuffer config = new StringBuffer();
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            TrackerAddressHolder holder = trackerAddressCircular.next();
            InetSocketAddress address = holder.getAddress();
            config.append(address.toString()).append(",");
        }
        return new String(config);
    }

    /**
     * 设置连接有效
     */
    public void setActive(InetSocketAddress address) {
        TrackerAddressHolder holder = trackerAddressMap.get(address);
        holder.setActive();
    }

    /**
     * 设置连接无效
     */
    public void setInActive(InetSocketAddress address) {
        TrackerAddressHolder holder = trackerAddressMap.get(address);
        holder.setInActive();
    }

    public List<String> getTrackerList() {
        return Collections.unmodifiableList(trackerList);
    }

    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }

    public void setRetryAfterSecend(int retryAfterSecend) {
        this.retryAfterSecend = retryAfterSecend;
    }
}
