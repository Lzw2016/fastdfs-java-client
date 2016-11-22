package org.cleverframe.fastdfs.conn;

import org.cleverframe.fastdfs.exception.FastDfsConnectException;
import org.cleverframe.fastdfs.exception.FastDfsException;
import org.cleverframe.fastdfs.pool.ConnectionPool;
import org.cleverframe.fastdfs.pool.TrackerLocator;
import org.cleverframe.fastdfs.protocol.AbstractCommand;
import org.cleverframe.fastdfs.protocol.storage.StorageCommand;
import org.cleverframe.fastdfs.protocol.tracker.TrackerCommand;
import org.cleverframe.fastdfs.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * 连接池管理<br/>
 * 负责借出连接，在连接上执行业务逻辑，然后归还连<br/>
 * <b>注意: 当前类最好使用单例，一个应用只需要一个实例</b>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:26 <br/>
 */
public class DefaultCommandExecutor implements CommandExecutor {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandExecutor.class);

    /**
     * Tracker定位
     */
    private TrackerLocator trackerLocator;

    /**
     * 连接池
     */
    private ConnectionPool pool;

    /**
     * 构造函数
     *
     * @param trackerStr Tracker Server服务器IP地址,格式 host:port(多个用用“,”隔开)
     * @param pool       连接池
     */
    public DefaultCommandExecutor(String trackerStr, ConnectionPool pool) {
        logger.debug("初始化Tracker Server连接 {}", trackerStr);
        Set<String> trackerSet = new HashSet<String>();
        String[] trackerArray = StringUtils.split(trackerStr, ",");
        for (String tracker : trackerArray) {
            if (StringUtils.isBlank(tracker)) {
                continue;
            }
            trackerSet.add(tracker.trim());
        }
        if (trackerSet.size() <= 0) {
            throw new RuntimeException("Tracker Server服务器IP地址解析失败[" + trackerStr + "]");
        }
        this.pool = pool;
        trackerLocator = new TrackerLocator(trackerSet);
    }

    /**
     * 构造函数
     *
     * @param trackerSet Tracker Server服务器IP地址集合
     * @param pool       连接池
     */
    public DefaultCommandExecutor(Set<String> trackerSet, ConnectionPool pool) {
        logger.debug("初始化Tracker Server连接 {}", trackerSet);
        this.pool = pool;
        trackerLocator = new TrackerLocator(trackerSet);
    }

    @Override
    public <T> T execute(TrackerCommand<T> command) {
        Connection conn;
        InetSocketAddress address;
        try {
            // 获取Tracker服务器地址(使用轮询)
            address = trackerLocator.getTrackerAddress();
            // 从连接池中获取连接
            conn = getConnection(address);
        } catch (Throwable e) {
            throw new RuntimeException("获取Tracker服务器地址失败", e);
        }
        logger.debug("获取到Tracker连接地址{}", address);
        return executeCmd(address, conn, command);
    }

    @Override
    public <T> T execute(InetSocketAddress address, StorageCommand<T> command) {
        Connection conn;
        try {
            // 从连接池中获取连接
            conn = getConnection(address);
        } catch (Throwable e) {
            throw new RuntimeException("获取Storage服务器地址失败", e);
        }
        logger.debug("获取到Storage连接地址{}", address);
        return executeCmd(address, conn, command);
    }

    /**
     * 从连接池里获取连接<br/>
     * <b>注意: 返回的连接使用完必须还回给连接池, 调用pool.returnObject</b>
     *
     * @param address 连接池 资源KEY
     * @return 返回连接, 使用完必须返回给连接池
     */
    private Connection getConnection(InetSocketAddress address) {
        Connection conn;
        try {
            // 从连接池中获取连接
            conn = pool.borrowObject(address);
            trackerLocator.setActive(address, true);
        } catch (FastDfsConnectException e) {
            trackerLocator.setActive(address, false);
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException("从连接池中获取连接异常", e);
        }
        return conn;
    }

    /**
     * 在Server上执行命令, 执行完毕 把链接还回连接池
     *
     * @param address 连接池 资源KEY
     * @param conn    连接池连接资源
     * @param command Server命令对象
     * @return 返回请求响应对象
     */
    private <T> T executeCmd(InetSocketAddress address, Connection conn, AbstractCommand<T> command) {
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

    public ConnectionPool getPool() {
        return pool;
    }

    public void setPool(ConnectionPool pool) {
        this.pool = pool;
    }
}
