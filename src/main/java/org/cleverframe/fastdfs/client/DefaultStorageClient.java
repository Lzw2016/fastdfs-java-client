package org.cleverframe.fastdfs.client;

import org.cleverframe.fastdfs.model.FileInfo;
import org.cleverframe.fastdfs.model.MateData;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadCallback;

import java.io.InputStream;
import java.util.Set;

/**
 * 存储服务(Storage)客户端接口 默认实现<br/>
 * <b>注意: 当前类最好使用单例，一个应用只需要一个实例</b>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/21 16:25 <br/>
 */
public class DefaultStorageClient implements StorageClient {




    @Override
    public StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName) {
        return null;
    }

    @Override
    public StorePath uploadSlaveFile(String groupName, String masterFilename, InputStream inputStream, long fileSize, String prefixName, String fileExtName) {
        return null;
    }

    @Override
    public Set<MateData> getMetadata(String groupName, String path) {
        return null;
    }

    @Override
    public boolean overwriteMetadata(String groupName, String path, Set<MateData> metaDataSet) {
        return false;
    }

    @Override
    public boolean mergeMetadata(String groupName, String path, Set<MateData> metaDataSet) {
        return false;
    }

    @Override
    public FileInfo queryFileInfo(String groupName, String path) {
        return null;
    }

    @Override
    public boolean deleteFile(String groupName, String path) {
        return false;
    }

    @Override
    public <T> T downloadFile(String groupName, String path, DownloadCallback<T> callback) {
        return null;
    }

    @Override
    public <T> T downloadFile(String groupName, String path, long fileOffset, long fileSize, DownloadCallback<T> callback) {
        return null;
    }
}
