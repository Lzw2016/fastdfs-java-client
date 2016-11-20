package org.cleverframe.fastdfs.protocol.tracker;

import org.cleverframe.fastdfs.conn.Connection;
import org.cleverframe.fastdfs.testbase.GetConnection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:46 <br/>
 */
public class DeleteStorageCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetConnection.getDefaultConnection();
        try {
            DeleteStorageCommand command = new DeleteStorageCommand("group1", "192.168.56.139");
            command.execute(connection);
        } finally {
            connection.close();
        }
    }
}
