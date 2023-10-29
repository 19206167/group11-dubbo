package com.example.group11.controller.qa;

import com.example.group11.commons.utils.*;
import com.example.group11.entity.es.QaES;
import com.example.group11.entity.sql.Answer;
import com.example.group11.entity.sql.Question;
import com.example.group11.model.AnswerModel;
import com.example.group11.model.QuestionModel;
import com.example.group11.service.qa.QAService;
import com.example.group11.service.search.SearchService;
import com.example.group11.vo.AnswerVO;
import com.example.group11.vo.QuestionVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: AnswerController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description Answer Controller
 * @Date 2023/10/20 20:26
 */

@RestController
@Slf4j
@RequestMapping("/api/answer")
public class AnswerController {
    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    QAService qaService;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.search.SearchService.class, check = false)
    private SearchService searchService;


    @GetMapping("/questionId/{questionId}")
    @ApiOperation(notes = "得到答案（已测试）", value = "得到答案", tags = "问题管理")
    public RestResult<Answer> getAnswerByQuestionId(@PathVariable Integer questionId, HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);

        log.info(userId.toString());
        log.info(questionId.toString());

//        判断该问题答案能否被此用户获得
        boolean get = qaService.checkWhetherGetAnswer(userId, questionId);

        if (get) {
            Answer answer = qaService.getAnswerByQuestionId(questionId);
            log.info(answer.toString());
            return RestResult.ok(answer);
        } else {
            return RestResult.fail("您需要购买此问题的答案。");
        }
    }


    @GetMapping("/answerId/{answerId}")
    @ApiOperation(notes = "查找文字回答（已测试）", value = "查找文字回答", tags = "问题管理")
    public RestResult<Answer> getAnswerByAnswerId(@PathVariable Integer answerId){
        return RestResult.ok(qaService.findAnswerModelById(answerId));
    }

    @PostMapping("/audio/{questionId}")
    @ApiOperation(notes = "语音回答问题", value = "语音回答问题", tags = "问题管理")
    public RestResult<QuestionVO> answerQuestionAudio(@PathVariable Integer questionId, @RequestBody AnswerVO answerVO,
                                                     HttpServletRequest httpServletRequest) {
// 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        Integer userRole = JWTUtil.getUserRole(token);

//        获取url
        List<String> url = answerVO.getUrl();
        String answerContent = "";

        try {
            if (userRole.equals(1)) {//因为问题要存入es，因此把answerQuestionText放到controller执行
//                qaService.answerQuestionText(userId, questionId, answerContent);
                QuestionModel questionModel = qaService.findById(questionId);

//                添加回答，并且更新问题中的答案id
                AnswerModel answerModel = new AnswerModel(questionId, answerContent, 1, url);
                answerModel.setUserId(userId);
                add(questionModel, answerModel);
            } else {
                return RestResult.fail("非作者用户不能回答");
            }
        } catch (Exception e) {
            return RestResult.fail(e.getMessage());
        }
        return RestResult.ok();
    }

    @PostMapping("/text/{questionId}")
    @ApiOperation(notes = "文字回答问题（已测试）", value = "文字回答问题", tags = "问题管理")
    public RestResult<QuestionVO> answerQuestionText(@PathVariable Integer questionId, @RequestBody AnswerVO answerVO,
                                                 HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        Integer userRole = JWTUtil.getUserRole(token);

//        获取answer内容
        String answerContent = answerVO.getAnswerContent();

        try {
            if (userRole.equals(1)) {//因为问题要存入es，因此把answerQuestionText放到controller执行
//                qaService.answerQuestionText(userId, questionId, answerContent);
                QuestionModel questionModel = qaService.findById(questionId);

//        验证问题存在，且问题回答者与当前用户匹配
//                添加回答，并且更新问题中的答案id
                AnswerModel answerModel = new AnswerModel(questionId, answerContent, 0);
                answerModel.setUserId(userId);
                add(questionModel, answerModel);
            } else {
                return RestResult.fail("非作者用户不能回答");
            }
        } catch (Exception e) {
            return RestResult.fail(e.getMessage());
        }
        return RestResult.ok();
    }

    @PutMapping("/text/update/{questionId}")
    @ApiOperation(notes = "更新文字回答（已测试）", value = "更新文字回答", tags = "问题管理")
    public RestResult<Answer> updateTextQuestionAnswer(@PathVariable Integer questionId, @RequestBody AnswerVO answerVO,
                                                       HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        Integer userRole = JWTUtil.getUserRole(token);

//        获取stringContent
        String answerContent = answerVO.getAnswerContent();

//        更新文字回答
        Answer answer = qaService.updateTextQuestionAnswer(userId, questionId, answerVO.getUrl(), answerContent);

//        更新qs中的数据
        QaES qaES = searchService.getQaById(questionId.toString()).get();

        qaES.setAnswerContent(answerContent);

        searchService.saveQa(qaES);

//        返回更新的answer
        return RestResult.ok(answer);
    }

    private void add(QuestionModel questionModel, AnswerModel answerModel) {
        questionModel.setAnswerId(qaService.answerQuestion(answerModel).getId());
        qaService.updateById(questionModel);
//                        将问题添加到回es中
        QaES qaES = OrikaUtil.map(questionModel, QaES.class);
        OrikaUtil.map(answerModel, qaES);
        qaES.setId(questionModel.getId().toString());
        qaES.setQuestionContent(questionModel.getContent());
//                        语音回答，没有文字描述
        qaES.setAnswerContent(answerModel.getContent());
//                        暂时url长度都为1
        qaES.setQaUrl(StringUtils.join(answerModel.getUrl(), ","));
        searchService.saveQa(qaES);
    }
}