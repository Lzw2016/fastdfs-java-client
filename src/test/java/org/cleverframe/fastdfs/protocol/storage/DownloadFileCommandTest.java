package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.conn.Connection;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadFileWriter;
import org.cleverframe.fastdfs.protocol.tracker.GetGroupListCommandTest;
import org.cleverframe.fastdfs.testbase.GetStorageConnection;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 16:15 <br/>
 */
public class DownloadFileCommandTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GetGroupListCommandTest.class);

    @Test
    public void test01() {
        Connection connection = GetStorageConnection.getDefaultConnection();
        try {
            DownloadFileWriter callback = new DownloadFileWriter("Test.jpg");
            DownloadFileCommand<String> command = new DownloadFileCommand<String>("group1", "M00/00/00/wKg4i1gw1JWALQsHAATA4WNjQT4937_big.jpg", callback);
            String fileName = command.execute(connection);
        } finally {
            connection.close();
        }
    }

    @Test
    public void test02() {
        File file = new File("Test.jpg");
        logger.info(file.getAbsolutePath());
    }
}
