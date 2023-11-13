package com.example.group11.commons.utils;

import com.aliyun.oss.OSSClient;

import java.io.File;

/**
* FileName: OssFileUpload.java
* @Description Oss File upload
* @author 刘梓健
* @Date    2023/10/28 15:23
* @version 1.0
*/

public class OssFileUpload {
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    // accessKeyId和accessKeySecret
    private static String accessKeyId = "LTAI5tAHoRTQTJjEbqjGY91C";
    private static String accessKeySecret = "5ReLN0Qc4q1bCXynmkAWTnxLWMppFs";

    // 创建OSSClient实例。

    public String uploadFileToOss(String fileName, String bucketName, File file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            ossClient.putObject(bucketName, fileName, file);
            return endpoint + "/" + fileName;
        } catch (Exception e) {
            e.getMessage();
            throw new Group11Exception(ErrorCode.COMMON_ERROR, e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    public static String getEndpoint(){
        return endpoint;
    }
}
