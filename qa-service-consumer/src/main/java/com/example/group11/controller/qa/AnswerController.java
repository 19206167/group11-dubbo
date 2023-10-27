package com.example.group11.controller.qa;

import com.example.group11.commons.utils.*;
import com.example.group11.entity.es.QaES;
import com.example.group11.model.AnswerModel;
import com.example.group11.model.QuestionModel;
import com.example.group11.service.qa.QAService;
import com.example.group11.service.search.SearchService;
import com.example.group11.service.user.FollowService;
import com.example.group11.vo.AnswerVO;
import com.example.group11.vo.QuestionVO;
import com.example.group11.websocket.WebSocketServer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private WebSocketServer webSocketServer;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.user.FollowService.class)
    private FollowService followService;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    QAService qaService;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.search.SearchService.class)
    private SearchService searchService;

    @PostMapping("/text/{questionId}")
    @ApiOperation(notes = "回答问题", value = "回答问题", tags = "问题管理")
    public RestResult<QuestionVO> answerQuestion(@PathVariable Integer questionId, @RequestBody AnswerVO answerVO,
                                                 HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        Integer userRole = JWTUtil.getUserRole(token);

        String answerContent = answerVO.getAnswerContent();

        try {
            if (userRole.equals(1)) {//因为问题要存入es，因此把answerQuestionText放到controller执行
//                qaService.answerQuestionText(userId, questionId, answerContent);
                QuestionModel questionModel = qaService.findById(questionId);

//        验证问题存在，且问题回答者与当前用户匹配
                if (CheckUtil.isNotEmpty(questionModel) && CheckUtil.isNotEmpty(questionModel.getResponderId())) {
                    if (questionModel.getResponderId().equals(userId)) {
//                添加回答，并且更新问题中的答案id
                        AnswerModel answerModel = new AnswerModel(questionId, answerContent, 0);
                        questionModel.setAnswerId(qaService.insertAnswer(answerModel));
                        qaService.updateById(questionModel);
                        QaES qaES = OrikaUtil.map(questionModel, QaES.class);
                        OrikaUtil.map(answerModel, qaES);
                        qaES.setId(questionId.toString());
                        qaES.setQuestionContent(questionModel.getContent());
                        qaES.setAnswerContent(answerContent);
                        qaES.setQaUrl(StringUtils.join(answerModel.getUrl(), ","));
                        searchService.saveQa(qaES);
                        webSocketServer.sendTo("有答主已经解答了您的问题", questionModel.getAskerId().toString());

                        List<String> followingResponderUserIdList = followService.queryFollowingIdByFollowedId(userId).stream().
                                map(String::valueOf).collect(Collectors.toList());
                        webSocketServer.sendTo("您关注的用户回答了新的问题", followingResponderUserIdList);

                        List<String> followingAskerUserIdList = followService.queryFollowingIdByFollowedId(questionModel.getAskerId()).stream().
                                map(String::valueOf).collect(Collectors.toList());
                        webSocketServer.sendTo("您关注的用户的提问被回答了", followingAskerUserIdList);
                    } else {
                        throw new Group11Exception(ErrorCode.USER_ROLE_ERROR);
                    }
                } else {
                    throw new Group11Exception(ErrorCode.EMPTY_RESULT);
                }
            } else {
                return RestResult.fail("非作者用户不能回答");
            }
        } catch (Exception e) {
            return RestResult.fail(e.getMessage());
        }
        return RestResult.ok();
    }
}
