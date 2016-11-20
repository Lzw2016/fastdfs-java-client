package org.cleverframe.fastdfs.protocol.storage;

import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.BaseResponse;
import org.cleverframe.fastdfs.protocol.storage.request.UploadSlaveFileRequest;

import java.io.InputStream;

/**
 * 从文件上传命令<br/>
 * <pre>
 * 使用背景
 * 使用FastDFS存储一个图片的多个分辨率的备份时，希望只记录源图的FID，
 * 并能将其它分辨率的图片与源图关联。可以使用从文件方法
 * 名词注解:
 *   主从文件是指文件ID有关联的文件，一个主文件可以对应多个从文件
 *   主文件ID = 主文件名 + 主文件扩展名
 *   从文件ID = 主文件名 + 从文件后缀名 + 从文件扩展名
 * 以缩略图场景为例：主文件为原始图片，从文件为该图片的一张或多张缩略图
 * 流程说明：
 *  1.先上传主文件（即：原文件），得到主文件FID
 *  2.然后上传从文件（即：缩略图），指定主文件FID和从文件后缀名，上传后得到从文件FID。
 *
 * 注意:
 *   FastDFS中的主从文件只是在文件ID上有联系。FastDFS server端没有记录主从文件对应关系，
 *   因此删除主文件，FastDFS不会自动删除从文件。删除主文件后，从文件的级联删除，需要由应用端来实现。
 * </pre>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 19:06 <br/>
 */
public class UploadSlaveFileCommand extends StorageCommand<StorePath> {

    /**
     * 文件上传命令
     *
     * @param inputStream    输入流
     * @param fileSize       文件大小
     * @param masterFilename 主文件名称(路径)
     * @param prefixName     从文件前缀
     * @param fileExtName    文件扩展名
     */
    public UploadSlaveFileCommand(InputStream inputStream, long fileSize, String masterFilename, String prefixName, String fileExtName) {
        super();
        this.request = new UploadSlaveFileRequest(inputStream, fileSize, masterFilename, prefixName, fileExtName);
        // 输出响应
        this.response = new BaseResponse<StorePath>() {
        };
    }
}