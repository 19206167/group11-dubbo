package com.example.group11.service.file.impl;

import com.example.group11.commons.utils.OssFileUpload;
import com.example.group11.commons.utils.OssUtil;
import com.example.group11.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.File;

/**
 * FileName: FileService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description file service
 * @Date 2023/10/14 18:31
 */
@DubboService(version = "1.0.0", interfaceClass = com.example.group11.service.file.FileService.class, timeout = 10000)
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadPicture(String bucketName, String fileName, File file) {
        OssFileUpload ossFileUpload = new OssFileUpload();
        log.info(fileName);
        return ossFileUpload.uploadFileToOss(fileName, bucketName, file);
    }

    @Override
    public String uploadAudio(String bucketName, String fileName, File file) {
        OssFileUpload ossFileUpload = new OssFileUpload();
        return ossFileUpload.uploadFileToOss(fileName, bucketName, file);
    }
}
