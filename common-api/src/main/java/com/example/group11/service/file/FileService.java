package com.example.group11.service.file;

import java.io.File;

/**
 * FileName: FileService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 17:46
 */
public interface FileService {
//    上传文件, 返回url
//    path为oss路径，localPath为文件在本地的路径
    String uploadPicture(String bucketName, String fileName, File file);

    String uploadAudio(String bucketName, String fileName, File file);
}
