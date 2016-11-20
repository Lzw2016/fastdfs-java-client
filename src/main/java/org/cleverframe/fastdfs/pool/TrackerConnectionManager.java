package org.cleverframe.fastdfs.pool;

import org.cleverframe.fastdfs.conn.Connection;
import org.cleverframe.fastdfs.exception.FastDfsConnectException;
import org.cleverframe.fastdfs.protocol.FastDFSCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理TrackerClient连接池分配<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:35 <br/>
 */
public class TrackerConnectionManager extends ConnectionManager {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TrackerConnectionManager.class);

    /**
     * Tracker定位
     */
    private TrackerLocator trackerLocator;

    /**
     * tracker服务配置地址列表
     */
    private List<String> trackerList = new ArrayList<String>();

    /**
     * 构造函数
     */
    public TrackerConnectionManager() {
        super();
    }

    /**
     * 构造函数
     */
    public TrackerConnectionManager(FastDfsConnectionPool pool) {
        super(pool);
    }

    /**
     * 初始化方法
     */
    public void initTracker() {
        logger.debug("初始化Tracker Server连接 {}", trackerList);
        trackerLocator = new TrackerLocator(trackerList);
    }

    /**
     * 获取连接并执行交易
     *
     * @param command FastDFS命令执行对象
     * @return 返回请求响应对象
     */
    public <T> T executeTrackerCmd(FastDFSCommand<T> command) {
        Connection conn;
        InetSocketAddress address = null;
        // 获取连接
        try {
            address = trackerLocator.getTrackerAddress();
            logger.debug("获取到Tracker连接地址{}", address);
            conn = getConnection(address);
            trackerLocator.setActive(address);
        } catch (FastDfsConnectException e) {
            trackerLocator.setInActive(address);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("从连接池中获取连接失败", e);
        }
        // 执行交易
        return execute(address, conn, command);
    }

    public List<String> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }
}
