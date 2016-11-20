package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.conn.Connection;
import org.cleverframe.fastdfs.model.StorageNodeInfo;
import org.cleverframe.fastdfs.testbase.GetTrackerConnection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:37 <br/>
 */
public class GetFetchStorageCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetTrackerConnection.getDefaultConnection();
        try {
            GetFetchStorageCommand command = new GetFetchStorageCommand("group1", "/00/00/wKg4i1gw1JWALQsHAATA4WNjQT4937.jpg", false);
            StorageNodeInfo storageNodeInfo = command.execute(connection);
            logger.info(storageNodeInfo.toString());
        } finally {
            connection.close();
        }
    }
}
