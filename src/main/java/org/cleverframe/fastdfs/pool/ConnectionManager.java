package org.cleverframe.fastdfs.pool;

import org.cleverframe.fastdfs.conn.Connection;
import org.cleverframe.fastdfs.exception.FastDfsConnectException;
import org.cleverframe.fastdfs.exception.FastDfsException;
import org.cleverframe.fastdfs.protocol.tracker.TrackerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * 连接池管理<br/>
 * 负责借出连接，在连接上执行业务逻辑，然后归还连<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:26 <br/>
 */
public class ConnectionManager {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    /**
     * Tracker定位
     */
    private TrackerLocator trackerLocator;

    /**
     * 连接池
     */
    private FastDfsConnectionPool pool;

    /**
     * 构造函数
     *
     * @param trackerSet Tracker Server服务器IP地址集合
     * @param pool       连接池
     */
    public ConnectionManager(Set<String> trackerSet, FastDfsConnectionPool pool) {
        logger.debug("初始化Tracker Server连接 {}", trackerSet);
        this.pool = pool;
        trackerLocator = new TrackerLocator(trackerSet);
    }

    /**
     * 获取连接
     *
     * @param address 请求地址
     * @return 连接
     */
    private Connection getConnection(InetSocketAddress address) {
        Connection conn;
        try {
            // 获取连接
            conn = pool.borrowObject(address);
        } catch (FastDfsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("从连接池中获取连接异常", e);
        }
        return conn;
    }

    /**
     * 获取连接并执行交易
     *
     * @param command FastDFS命令执行对象
     * @return 返回请求响应对象
     */
    public <T> T execute(TrackerCommand<T> command) {
        Connection conn;
        InetSocketAddress address = null;
        // 获取连接
        try {
            address = trackerLocator.getTrackerAddress();
            logger.debug("获取到Tracker连接地址{}", address);
            conn = getConnection(address);
            trackerLocator.setActive(address, true);
        } catch (FastDfsConnectException e) {
            trackerLocator.setActive(address, false);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("从连接池中获取连接失败", e);
        }
        // 发送请求
        try {
            logger.debug("发送请求, 服务器地址[{}], 请求类型[{}]", address, command.getClass().getSimpleName());
            return command.execute(conn);
        } catch (FastDfsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("发送FastDFS请求异常", e);
        } finally {
            try {
                if (null != conn) {
                    pool.returnObject(address, conn);
                }
            } catch (Exception e) {
                logger.error("归还连接到连接池失败", e);
            }
        }
    }

    /**
     * 获取连接池信息
     */
    public void dumpPoolInfo() {
        if (logger.isDebugEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# ------Dump Pool Info------\r\n" +
                    "#\t 活动连接：" + pool.getNumActive() + "\r\n" +
                    "#\t 空闲连接：" + pool.getNumIdle() + "\r\n" +
                    "#\t 正在使用的连接：" + pool.getNumWaiters() + "\r\n" +
                    "#\t 连接获取总数统计：" + pool.getBorrowedCount() + "\r\n" +
                    "#\t 连接返回总数统计：" + pool.getReturnedCount() + "\r\n" +
                    "#\t 连接创建总数统计：" + pool.getCreatedCount() + "\r\n" +
                    "#\t 连接销毁总数统计：" + pool.getDestroyedCount() + "\r\n" +
                    "#\t 连接销毁(因为连接不可用)总数统计：" + pool.getDestroyedByBorrowValidationCount() + "\r\n" +
                    "#\t 连接销毁(因为连接被回收)总数统计：" + pool.getDestroyedByEvictorCount() + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.debug(tmp);
        }
    }

    public FastDfsConnectionPool getPool() {
        return pool;
    }

    public void setPool(FastDfsConnectionPool pool) {
        this.pool = pool;
    }
}
