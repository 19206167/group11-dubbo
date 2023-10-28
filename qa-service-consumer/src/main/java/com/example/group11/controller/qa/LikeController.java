package com.example.group11.controller.qa;

import com.example.group11.commons.utils.JWTUtil;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.service.qa.QAService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * FileName: LikeController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/26 20:31
 */

@RestController
@Slf4j
@RequestMapping("/api/like")
public class LikeController {
    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    QAService qaService;

    @PostMapping("/{questionId}")
    @ApiOperation(notes = "点赞", value = "点赞", tags = "点赞管理")
    public RestResult<Boolean> like(@PathVariable Integer questionId, HttpServletRequest httpServletRequest) {

        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);


        if (qaService.like(userId, questionId)){
            return RestResult.ok(true);
        } else {
            return RestResult.fail("不能重复点赞");
        }
    }

    @DeleteMapping("/{questionId}")
    @ApiOperation(notes = "取消点赞", value = "取消点赞", tags = "点赞管理")
    public RestResult<Boolean> cancelLike(@PathVariable Integer questionId, HttpServletRequest httpServletRequest) {

        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);

        if (qaService.cancelLike(userId, questionId)){
            return RestResult.ok(true);
        } else {
            return RestResult.fail("还没有点赞，不能取消");
        }
    }

    @GetMapping("/num/{questionId}")
    @ApiOperation(notes = "点赞数量", value = "点赞数量", tags = "点赞管理")
    public RestResult<Integer> likeNum(@PathVariable Integer questionId) {
        return RestResult.ok(qaService.getLikeNum(questionId));
    }
}
