package org.cleverframe.fastdfs.client;

import org.cleverframe.fastdfs.model.FileInfo;
import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadCallback;

import java.io.InputStream;
import java.util.Set;

/**
 * 存储服务(Storage)客户端接口
 * 作者：LiZW <br/>
 * 创建时间：2016/11/21 16:11 <br/>
 */
public interface StorageClient {
    /**
     * 上传文件<br/>
     * 文件上传后不可以修改，如果要修改则删除以后重新上传
     *
     * @param groupName   组名称
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @return 文件存储路径
     */
    StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName);

    /**
     * 上传从文件
     *
     * @param groupName      组名称
     * @param masterFilename 主文件路径
     * @param inputStream    从文件输入流
     * @param fileSize       从文件大小
     * @param prefixName     从文件前缀
     * @param fileExtName    主文件扩展名
     * @return 文件存储路径
     */
    StorePath uploadSlaveFile(String groupName, String masterFilename, InputStream inputStream, long fileSize, String prefixName, String fileExtName);

    /**
     * 获取文件元信息
     *
     * @param groupName 组名称
     * @param path      主文件路径
     * @return 获取文件元信息集合，不存在返回空集合
     */
    Set<MateData> getMetadata(String groupName, String path);

    /**
     * 修改文件元信息（覆盖）
     *
     * @param groupName   组名称
     * @param path        主文件路径
     * @param metaDataSet 元信息集合
     */
    boolean overwriteMetadata(String groupName, String path, Set<MateData> metaDataSet);

    /**
     * 修改文件元信息（合并）
     *
     * @param groupName   组名称
     * @param path        主文件路径
     * @param metaDataSet 元信息集合
     */
    boolean mergeMetadata(String groupName, String path, Set<MateData> metaDataSet);

    /**
     * 获取文件的信息
     *
     * @param groupName 组名称
     * @param path      主文件路径
     * @return 文件信息(不存在返回null)
     */
    FileInfo queryFileInfo(String groupName, String path);

    /**
     * 删除文件
     *
     * @param groupName 组名称
     * @param path      主文件路径
     */
    boolean deleteFile(String groupName, String path);

    /**
     * 下载整个文件
     *
     * @param groupName 组名称
     * @param path      主文件路径
     * @param callback  下载回调接口
     * @return 下载回调接口返回结果
     */
    <T> T downloadFile(String groupName, String path, DownloadCallback<T> callback);

    /**
     * 下载文件片段(断点续传)
     *
     * @param groupName  组名称
     * @param path       主文件路径
     * @param fileOffset 开始位置
     * @param fileSize   文件大小(经过测试好像这个参数值只能是“0”)
     * @param callback   下载回调接口
     * @return 下载回调接口返回结果
     */
    <T> T downloadFile(String groupName, String path, long fileOffset, long fileSize, DownloadCallback<T> callback);

    // ----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 上传文件， 并设置文件元数据
     *
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @param metaDataSet 元信息集合
     * @return 文件存储路径
     */
    StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MateData> metaDataSet);

    /**
     * 文件上传(支持断点续传)
     *
     * @param groupName   组名称
     * @param inputStream 文件输入流(文件部分)
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @return 文件存储路径
     */
    StorePath uploadAppenderFile(String groupName, InputStream inputStream, long fileSize, String fileExtName);

    /**
     * 断点续传文件
     *
     * @param groupName   组名称
     * @param path        文件路径
     * @param inputStream 文件输入流(文件部分)
     * @param fileSize    文件大小
     */
    void appendFile(String groupName, String path, InputStream inputStream, long fileSize);

    /**
     * 修改续传文件的内容
     *
     * @param groupName   组名称
     * @param path        文件路径
     * @param inputStream 文件输入流
     * @param fileSize    文件大小
     * @param fileOffset  开始位置
     */
    void modifyFile(String groupName, String path, InputStream inputStream, long fileSize, long fileOffset);

    /**
     * 清除续传类型文件的内容
     *
     * @param groupName         组名称
     * @param path              文件路径
     * @param truncatedFileSize 截断文件大小
     */
    void truncateFile(String groupName, String path, long truncatedFileSize);

    /**
     * 清除续传类型文件的内容
     *
     * @param groupName 组名称
     * @param path      文件路径
     */
    void truncateFile(String groupName, String path);
}
