package org.cleverframe.fastdfs.pool;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.fastdfs.exception.FastDfsUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 表示Tracker服务器位置<br/>
 * 支持负载均衡对IP轮询<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:58 <br/>
 */
public class TrackerLocator {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TrackerLocator.class);

    /**
     * 连接不可经过10分钟以后重试连接
     */
    private static final int DEFAULT_RETRY_AFTER_SECOND = 10 * 60;

    /**
     * 连接中断以后经过N秒后可以重试
     */
    private int retryAfterSecond = DEFAULT_RETRY_AFTER_SECOND;

    /**
     * 方便随意快速读取
     */
    private Map<String, TrackerAddressState> trackerAddressMap = new HashMap<String, TrackerAddressState>();

    /**
     * 轮询圈(用户轮询获取连接地址)
     */
    private CircularList<TrackerAddressState> trackerAddressCircular = new CircularList<TrackerAddressState>();

    /**
     * 初始化Tracker服务器地址
     * 配置方式为 ip:port 如 192.168.1.2:21000
     */
    public TrackerLocator(Set<String> trackerSet) {
        logger.debug("开始初始化Tracker Server地址:{}", trackerSet);
        for (String addressStr : trackerSet) {
            if (StringUtils.isBlank(addressStr)) {
                continue;
            }
            String[] parts = StringUtils.split(addressStr, ":", 2);
            if (parts.length != 2) {
                logger.warn("Tracker Server地址格式无效[{}], 跳过此配置(正确格式 host:port)", addressStr);
                continue;
            }
            InetSocketAddress address;
            try {
                address = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            } catch (Throwable e) {
                logger.warn("创建InetSocketAddress失败, Tracker Server地址[{}], 跳过此配置", addressStr);
                continue;
            }
            if (trackerAddressMap.get(addressStr) == null) {
                TrackerAddressState holder = new TrackerAddressState(address);
                trackerAddressCircular.add(holder);
                trackerAddressMap.put(address.toString(), holder);
            }
        }
        if (logger.isDebugEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# 初始化Tracker Server地址完毕\r\n" +
                    "#\t trackerAddressMap#keySet: " + trackerAddressMap.keySet() + "\r\n" +
                    "#\t trackerAddressCircular: " + trackerAddressCircular + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.debug(tmp);
        }
    }

    /**
     * 获取Tracker服务器地址
     */
    public InetSocketAddress getTrackerAddress() {
        TrackerAddressState holder;
        // 遍历连接地址,抓取当前有效的地址
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            holder = trackerAddressCircular.next();
            if (holder.canTryToConnect(retryAfterSecond)) {
                return holder.getAddress();
            }
        }
        throw new FastDfsUnavailableException("找不到可用的 Tracker Server - {}" + trackerAddressMap.keySet());
    }

    /**
     * 设置连接是否有效
     *
     * @param address   连接地址
     * @param available true有效，false无效
     */
    public void setActive(InetSocketAddress address, boolean available) {
        TrackerAddressState holder = trackerAddressMap.get(address.toString());
        if (holder == null) {
            logger.warn("TrackerAddressMap获取TrackerAddressState为null, key={}, 设置连接是否有效失败[{}]", address, available);
            return;
        }
        holder.setAvailable(available);
    }

    public void setRetryAfterSecond(int retryAfterSecond) {
        this.retryAfterSecond = retryAfterSecond;
    }
}
