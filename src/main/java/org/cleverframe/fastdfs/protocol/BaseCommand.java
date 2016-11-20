package org.cleverframe.fastdfs.protocol;

import org.cleverframe.fastdfs.conn.Connection;

/**
 * FastDFS命令操执行接口
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 0:57 <br/>
 */
public interface BaseCommand<T> {

    /**
     * 执行FastDFS命令
     */
    T execute(Connection conn);
}
