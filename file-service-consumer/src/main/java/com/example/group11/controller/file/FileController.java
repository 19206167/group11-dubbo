package com.example.group11.controller.file;

import com.example.group11.commons.utils.RestResult;
import com.example.group11.vo.FileVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/file")
@CrossOrigin(value = "*", maxAge = 3600)
public class FileController {

    /**
     * 文件上传
     */
    @PostMapping(value = "/upload")
    @ApiOperation(notes = "统一文件上传接口", value = "统一文件上传接口", tags = "文件管理")
    public RestResult<FileVO> uploadFile(@RequestParam("file") MultipartFile file) {
        return RestResult.ok();
    }

    /**
     * 文件下载
     */
    @GetMapping("download/{fileId}")
    @ApiOperation(notes = "统一文件下载接口", value = "统一文件下载接口", tags = "文件管理")
    public void downloadFile(@PathVariable Long fileId, HttpServletResponse response) throws IOException {

    }


}

