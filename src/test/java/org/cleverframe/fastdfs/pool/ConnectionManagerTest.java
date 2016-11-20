package org.cleverframe.fastdfs.pool;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.cleverframe.fastdfs.model.StorageNode;
import org.cleverframe.fastdfs.protocol.tracker.GetStorageNodeCommand;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 20:45 <br/>
 */
public class ConnectionManagerTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerTest.class);

    private static String[] ips = {"192.168.56.139:22122"};
    private static List<String> trackerIpList = Arrays.asList(ips);

    @Test
    public void test() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectTimeout(500);
        pooledConnectionFactory.setSoTimeout(500);

        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(20);
        FastDfsConnectionPool connectionPool = new FastDfsConnectionPool(pooledConnectionFactory, conf);

        TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager(connectionPool);
        trackerConnectionManager.setTrackerList(trackerIpList);
        trackerConnectionManager.initTracker();

        trackerConnectionManager.dumpPoolInfo();

        GetStorageNodeCommand command = new GetStorageNodeCommand();
        StorageNode storageNode = trackerConnectionManager.executeTrackerCmd(command);
        logger.info(storageNode.toString());

        trackerConnectionManager.dumpPoolInfo();

        connectionPool.close();
    }
}
