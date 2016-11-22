# fastdfs-java-client(FastDFS文件服务器Java客户端)
---
## 介绍 ##


## 主要接口和设计思想 ##



## 使用示例 ##


## 与Spring整合 ##

`cleverframe-filemanager.properties` 配置文件

``` python
#Tracker Server IP地址
fileupload.FastDFS.trackers=192.168.10.128:22122,192.168.10.129:22122
#连接soTimeout设置
fileupload.FastDFS.soTimeout=10000
#连接超时设置
fileupload.FastDFS.connectTimeout=5000
#连接池 maxTotal
fileupload.FastDFS.maxTotal=200
#连接池 maxTotalPerKey
fileupload.FastDFS.maxTotalPerKey=200
#连接池 maxIdlePerKey 最大空闲连接数(影响并发性能)
fileupload.FastDFS.maxIdlePerKey=50
```


`spring-context-filemanager.xml` 配置文件

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-4.2.xsd"
       default-lazy-init="false">

    <!--FastDFS连接池配置-->
    <bean id="fastDfsConnectionPool" class="org.cleverframe.fastdfs.pool.ConnectionPool" destroy-method="close">
        <constructor-arg index="0" type="org.apache.commons.pool2.KeyedPooledObjectFactory">
            <bean class="org.cleverframe.fastdfs.pool.PooledConnectionFactory">
                <constructor-arg index="0" type="int" value="${fileupload.FastDFS.soTimeout}"/><!--soTimeout-->
                <constructor-arg index="1" type="int" value="${fileupload.FastDFS.connectTimeout}"/><!--connectTimeout-->
            </bean>
        </constructor-arg>
        <constructor-arg index="1" type="org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig">
            <bean class="org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig">
                <property name="maxTotal" value="${fileupload.FastDFS.maxTotal}"/>
                <property name="maxTotalPerKey" value="${fileupload.FastDFS.maxTotalPerKey}"/>
                <property name="maxIdlePerKey" value="${fileupload.FastDFS.maxIdlePerKey}"/>
            </bean>
        </constructor-arg>
    </bean>

    <!--FastDfs命令执行器-->
    <bean id="fastDfsCommandExecutor" class="org.cleverframe.fastdfs.conn.DefaultCommandExecutor">
        <constructor-arg index="0" type="java.lang.String" value="${fileupload.FastDFS.trackers}"/>
        <constructor-arg index="1" type="org.cleverframe.fastdfs.pool.ConnectionPool" ref="fastDfsConnectionPool"/>
    </bean>

    <!--FastDFS Tracker Client-->
    <bean id="fastDfsTrackerClient" class="org.cleverframe.fastdfs.client.DefaultTrackerClient">
        <constructor-arg index="0" type="org.cleverframe.fastdfs.conn.CommandExecutor" ref="fastDfsCommandExecutor"/>
    </bean>

    <!--FastDFS Storage Client-->
    <bean id="fastDfsStorageClient" class="org.cleverframe.fastdfs.client.DefaultStorageClient">
        <constructor-arg index="0" type="org.cleverframe.fastdfs.conn.CommandExecutor" ref="fastDfsCommandExecutor"/>
        <constructor-arg index="1" type="org.cleverframe.fastdfs.client.TrackerClient" ref="fastDfsTrackerClient"/>
    </bean>
</beans>
```




## 特别鸣谢 ##














https://github.com/tobato/FastDFS_Client
