# fastdfs-java-client(FastDFS文件服务器Java客户端)
---
## 介绍 ##
**FastDFS简介**
> FastDFS是一款类似Google FS的开源分布式文件系统，是纯C语言开发的。
> FastDFS是一个开源的轻量级分布式文件系统，它对文件进行管理，功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等，解决了大容量存储和负载均衡的问题。特别适合以文件为载体的在线服务，如相册网站、视频网站等等。
> 官方论坛  http://bbs.chinaunix.net/forum-240-1.html

由于官方提供的Java客户端代码没有实现连接池(高并发会有性能问题)，而且官方提供的代码风格类似c语言，结构也不太好，难以使用到线上项目中。所以参考官方的代码自己实现了，同时还参考了github上[tobato][1]的[FastDFS_Client][2]项目。

[FastDFS_Client][3]项目的缺点就是依赖的框架太多了！(依赖SpringBoot、commons-io、commons-pool2、hibernate-validator、thumbnailator等)很不适合单独使用或灵活使用，所以我参考了他的源码重构了项目，重构之后只依赖commons-pool2。

**主要特性**

 1. 必须使用JDK1.7及以上版本
 2. 支持对服务端的连接池管理(commons-pool2实现)
 3. 文件上传和下载都支持断点续传，也支持监控FastDFS集群的状态
 4. 代码结构清晰易读，容易使用，且适合二次开发自定义，可与Spring整合

## 主要接口和设计思想 ##
**包结构说明**
```
cfg           - 配置相关(未使用)
client        - 封装 StorageClient TrackerClient
conn          - 服务端连接相关
constant      - 通信常量定义
exception     - 定义异常
mapper        - 请求响应数据与model映射相关
model         - 请求响应model封装
pool          - 连接池相关
protocol      - 请求响应协议相关
    storage   - storage 请求响应协议相关
    tracker   - tracker 请求响应协议相关
utils         - 工具类定义
```

**主要接口介绍**
```
Connection          - 与服务器连接接口
ProtocolHead        - 协议请求响应数据头
BaseRequest         - 请求对象接口
BaseResponse        - 响应对象接口
BaseCommand         - 命令执行接口
AbstractCommand     - 命令执行抽象类
StorageCommand      - storage 命令执行抽象类
TrackerCommand      - tracker 命令执行抽象类
CommandExecutor     - 命令执行器接口 (用来执行命令)
DownloadCallback    - 文件下载回调接口
StorageClient       - storage 客户端(封装与Storage Server的各种操作)
TrackerClient       - tracker 客户端(封装与Tracker Server的各种操作)
```


## 使用示例 ##
``` java
// 连接创建工厂（用户新建连接）
PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 500);
// 连接池配置
GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
conf.setMaxTotal(200);
conf.setMaxTotalPerKey(200);
conf.setMaxIdlePerKey(100);
// 连接池
ConnectionPool connectionPool = new ConnectionPool(pooledConnectionFactory, conf);
Set<String> trackerSet = new HashSet<String>();
trackerSet.add("192.168.10.128:22122");
// 命令执行器
DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(trackerSet, connectionPool);
// Tracker客户端
TrackerClient trackerClient = new DefaultTrackerClient(commandExecutor);
// Storage客户端
StorageClient storageClient = new DefaultStorageClient(commandExecutor, trackerClient);

// Tracker客户端 - 获取Storage服务器节点信息
StorageNode storageNode = trackerClient.getStorageNode("group1");

// 获取文件信息
StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage("group1", "M00/00/00/wKg4i1gxz_6AIPPsAAiCQSk77jI661.png");

// 上传文件
File file = new File("F:\\123456.txt");
FileInputStream fileInputStream = FileUtils.openInputStream(file);
StorePath storePath = storageClient.uploadFile("group1", fileInputStream, file.length(), "txt");

// 下载文件
DownloadFileWriter downloadFileWriter = new DownloadFileWriter("F:\\123.xlsx");
String filePath = storageClient.downloadFile("group1", "M00/00/00/wKgKgFg02TaAY3mTADCUhuWQdRc53.xlsx", downloadFileWriter);

// ... 更多操作

// 关闭连接池
connectionPool.close();
```

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
接着就可以获取注入的`fastDfsStorageClient` 和 `fastDfsTrackerClient` 对FastDFS进行操作了

## 特别鸣谢 ##
(1) happyfish100 [https://github.com/happyfish100/fastdfs-client-java][4]
(2) tobato [https://github.com/tobato/FastDFS_Client][2]


  [1]: https://github.com/tobato
  [2]: https://github.com/tobato/FastDFS_Client
  [3]: https://github.com/tobato/FastDFS_Client
  [4]: https://github.com/happyfish100/fastdfs-client-java