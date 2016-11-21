package org.cleverframe.fastdfs.client;

import org.cleverframe.fastdfs.model.GroupState;
import org.cleverframe.fastdfs.model.StorageNode;
import org.cleverframe.fastdfs.model.StorageNodeInfo;
import org.cleverframe.fastdfs.model.StorageState;

import java.util.List;

/**
 * 目录服务(Tracker)客户端接口
 * 作者：LiZW <br/>
 * 创建时间：2016/11/21 15:45 <br/>
 */
public interface TrackerClient {
    /**
     * 获取一个存储节点信息
     *
     * @return 存储节点信息
     */
    StorageNode getStorageNode();

    /**
     * 根据组名称获取一个Group下面的一个存储节点名称
     *
     * @param groupName 组名称
     * @return 存储节点信息, 不存在返回null
     */
    StorageNode getStorageNode(String groupName);

    /**
     * 获取文件存储的源存储节信息
     *
     * @param groupName 组名称
     * @param filename  文件路径
     * @return 源存储节点信息
     */
    StorageNodeInfo getFetchStorage(String groupName, String filename);

    /**
     * 获取文件存储的源存储节信息(toUpdate=true)
     *
     * @param groupName 组名称
     * @param filename  文件路径
     * @return 源存储节点信息
     */
    StorageNodeInfo getFetchStorageAndUpdate(String groupName, String filename);

    /**
     * 获取存储组的状态
     *
     * @return 存储组状态集合，不存在返回空集合
     */
    List<GroupState> getGroupStates();

    /**
     * 获取一个存储组里的所有存储服务节点状态信息
     *
     * @param groupName 组名称
     * @return 存储节点状态信息集合，不存在返回空集合
     */
    List<StorageState> getStorageStates(String groupName);

    /**
     * 获取一个存储组里的某个存储服务节点状态信息
     *
     * @param groupName 组名称
     * @param storageIp 储服节点IP地址
     * @return 存储节点状态信息，不存在返回null
     */
    StorageState getStorageState(String groupName, String storageIp);

    /**
     * 把存储节点踢出到集群之外(此存储节点必须已关闭才能进行此操作，不然操作会失败)
     *
     * @param groupName 组名称
     * @param storageIp 储服节点IP地址
     * @return 返回true表示操作成功
     */
    boolean deleteStorage(String groupName, String storageIp);
}
