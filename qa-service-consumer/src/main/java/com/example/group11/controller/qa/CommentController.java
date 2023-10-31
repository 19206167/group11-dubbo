package com.example.group11.controller.qa;

import com.example.group11.commons.utils.JWTUtil;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.entity.sql.Comment;
import com.example.group11.service.qa.QAService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * FileName: CommentController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description Comment Controller
 * @Date 2023/10/26 20:31
 */

@RestController
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {
    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    QAService qaService;

    @PostMapping("/{questionId}/{content}")
    @ApiOperation(notes = "进行评论", value = "进行评论", tags = "评论管理")
    public RestResult<Boolean> like(@PathVariable Integer questionId, @PathVariable String content, HttpServletRequest httpServletRequest) {

        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);


        if (qaService.comment(userId, questionId, content)){
            return RestResult.ok(true);
        } else {
            return RestResult.fail("评论失败");
        }
    }

    @DeleteMapping("/{questionId}/{commentId}")
    @ApiOperation(notes = "删除评论", value = "删除评论", tags = "评论管理")
    public RestResult<Boolean> deleteComment(@PathVariable Integer questionId, @PathVariable Integer commentId, HttpServletRequest httpServletRequest) {

        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);

        if (qaService.deleteComment(userId, questionId, commentId)){
            return RestResult.ok(true);
        } else {
            return RestResult.fail("删除评论失败");
        }
    }

    @GetMapping("/num/{questionId}")
    @ApiOperation(notes = "评论数量", value = "评论数量", tags = "评论管理")
    public RestResult<Integer> commentNum(@PathVariable Integer questionId) {
        return RestResult.ok(qaService.getCommentNum(questionId));
    }

    @GetMapping("/list/{questionId}")
    @ApiOperation(notes = "分页获取评论", value = "分页获取评论", tags = "评论管理")
    public RestResult<Page<Comment>> getCommentsByPage(@PathVariable Integer questionId) {
        return RestResult.ok(qaService.getCommentByQuestionByPage(questionId, null, null));
    }
}
