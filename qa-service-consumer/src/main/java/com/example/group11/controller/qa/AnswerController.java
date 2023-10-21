package com.example.group11.controller.qa;

import com.example.group11.commons.utils.JWTUtil;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.service.qa.QAService;
import com.example.group11.vo.QuestionVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * FileName: AnswerController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/20 20:26
 */

@RestController
@Slf4j
@RequestMapping("/api/answer")
public class AnswerController {
    @DubboReference(version="1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    QAService qaService;

    @PostMapping("/text/{questionId}/{answerContent}")
    @ApiOperation(notes = "回答问题", value = "回答问题", tags = "问题管理")
    public RestResult<QuestionVO> answerQuestion(@RequestBody Integer questionId, @PathVariable String answerContent,
                                                 HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        Integer userRole = JWTUtil.getUserRole(token);

        try{
            if (userRole.equals(1)) {
                qaService.answerQuestionText(userId, questionId, answerContent);
            } else {
                return RestResult.fail("非作者用户不能回答");
            }
        } catch (Exception e) {
            return RestResult.fail(e.getMessage());
        }
        return RestResult.ok();
    }
}
