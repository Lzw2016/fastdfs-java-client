package org.cleverframe.fastdfs.pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.cleverframe.fastdfs.conn.Connection;
import org.cleverframe.fastdfs.conn.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 定义了被池化的对象的创建，初始化，激活，钝化以及销毁功能<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:33 <br/>
 */
public class PooledConnectionFactory extends BaseKeyedPooledObjectFactory<InetSocketAddress, Connection> {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PooledConnectionFactory.class);

    /**
     * 默认字符集
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * 设置默认字符集
     */
    private String charsetName = DEFAULT_CHARSET_NAME;

    /**
     * 读取时间
     */
    private int soTimeout;

    /**
     * 连接超时时间
     */
    private int connectTimeout;

    /**
     * 字符集
     */
    private Charset charset;

    public PooledConnectionFactory(int soTimeout, int connectTimeout, Charset charset) {
        this.soTimeout = soTimeout;
        this.connectTimeout = connectTimeout;
        this.charset = charset;
    }

    public PooledConnectionFactory(int soTimeout, int connectTimeout) {
        this(soTimeout, connectTimeout, null);
    }

    /**
     * 创建连接
     */
    @Override
    public Connection create(InetSocketAddress address) throws Exception {
        // 初始化字符集
        if (null == charset) {
            charset = Charset.forName(charsetName);
        }
        SocketConnection connection = new SocketConnection(address, soTimeout, connectTimeout, charset);
        logger.debug("新建连接[{}]", address);
        return connection;
    }

    /**
     * 将对象池化pooledObject
     */
    @Override
    public PooledObject<Connection> wrap(Connection conn) {
        return new DefaultPooledObject<Connection>(conn);
    }

    @Override
    public void destroyObject(InetSocketAddress key, PooledObject<Connection> p) throws Exception {
        p.getObject().close();
        logger.debug("关闭连接[{}]", key);
    }

    /**
     * 验证连接是否可用
     *
     * @param key 连接对应的Key
     * @param p   池化的对象
     */
    @Override
    public boolean validateObject(InetSocketAddress key, PooledObject<Connection> p) {
        boolean flag = p.getObject().isValid();
        logger.debug("验证连接是否可用[{]],验证结果[{}]", key, flag);
        return flag;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }
}
