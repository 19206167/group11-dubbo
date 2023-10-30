package com.example.group11.controller.file;

import com.example.group11.FileConsumerApplication;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.service.file.FileService;
import com.example.group11.vo.FileVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;


@RestController
@RequestMapping("/file")
@CrossOrigin(value = "*", maxAge = 3600)
@Slf4j
public class FileController {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.file.FileService.class, timeout = 10000)
    FileService fileService;

    /**
     * 文件上传
     */
    @PostMapping(value = "/upload")
    @ApiOperation(notes = "统一图片上传接口", value = "统一文件上传接口", tags = "文件管理")
    public RestResult<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        获取后缀用于判断是图片还是音频
        String suffix = file.getContentType().toLowerCase();
        suffix = suffix.substring(suffix.indexOf('/') + 1, suffix.length());

//        transfer file to local file and upload
//        String filePath = "\\logs";

        String fileName = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;

        log.info(fileName);

//        read file
        File local = new File(fileName);
        OutputStream os = null;
        InputStream ins = null;
        try {
            if (file.getSize() > 0) {
                os = new FileOutputStream(local);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                ins = file.getInputStream();
                while ((bytesRead = ins.read(buffer,0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try {
//            file.transferTo(local);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        log.info(suffix);
        String url;
        if (suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png")) {
            String tmp = "group11/image/" + fileName;
            log.info(tmp);
            url = fileService.uploadPicture("group11", tmp, local);
        } else if (suffix.equals("mp3")) {
            String tmp = "group11/image/" + fileName;
            log.info(tmp);
            url = fileService.uploadAudio("group11", tmp, local);
        } else {
            local.delete();
            return RestResult.fail("错误的文件类型");
        }
        local.delete();
        return RestResult.ok(url);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download/{fileId}")
    @ApiOperation(notes = "统一文件下载接口", value = "统一文件下载接口", tags = "文件管理")
    public void downloadFile(@PathVariable Long fileId, HttpServletResponse response) throws IOException {

    }


}

