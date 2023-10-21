package com.example.group11.controller.qa;


import com.example.group11.commons.utils.JWTUtil;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.entity.Question;
import com.example.group11.model.QuestionModel;
import com.example.group11.service.qa.QAService;
import com.example.group11.vo.QuestionDetailVO;
import com.example.group11.vo.QuestionVO;
import com.example.group11.vo.RespondentDetailVO;
import com.example.group11.vo.RespondentVO;
import com.example.group11.vo.query.QuestionQueryVO;
import com.example.group11.vo.query.RespondentQueryVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/questions")
public class QuestionController {
    
    @DubboReference(version="1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
    QAService qaService;

    @GetMapping("/all/question-list/{pageNo}/{pageSize}")
    @ApiOperation(notes = "根据多条件查询全部回答的分页列表", value = "根据多条件查询全部回答的分页列表", tags = "问题管理")
    public RestResult<Page<QuestionModel>> queryAllQuestionList(@PathVariable Integer pageNo, @PathVariable Integer pageSize,
                                                           HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);

        QuestionQueryVO questionQueryVO = new QuestionQueryVO();
        questionQueryVO.setAskerId(userId);
        questionQueryVO.setPageNo(pageNo);
        questionQueryVO.setPageSize(pageSize);

        Page<QuestionModel> questions = qaService.getUserQuestionsByPage(questionQueryVO);
        return RestResult.ok(questions);
    }

    @GetMapping("/all/respondent-list")
    @ApiOperation(notes = "根据多条件查询全部答主的分页列表", value = "根据多条件查询全部答主的分页列表", tags = "问题管理")
    public RestResult<Page<RespondentVO>> queryAllRespondentQuestionList(RespondentQueryVO params, HttpServletRequest httpServletRequest) {

        return RestResult.ok();
    }

    @GetMapping("/hot/question-list")
    @ApiOperation(notes = "首页热门回答列表", value = "首页热门回答列表", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryHotQuestionList() {
        return RestResult.ok();
    }

    @GetMapping("/hot/respondent-list")
    @ApiOperation(notes = "首页热门答主列表", value = "首页热门答主列表", tags = "问题管理")
    public RestResult<List<RespondentVO>> queryHotRespondentList() {
        return RestResult.ok();
    }

    @GetMapping("/{questionId}")
    @ApiOperation(notes = "问答详情", value = "问答详情", tags = "问题管理")
    public RestResult<QuestionDetailVO> queryQuestionDetail(@PathVariable Long questionId) {
        return RestResult.ok();
    }

    @GetMapping("/respondent/{id}")
    @ApiOperation(notes = "答主详情", value = "答主详情", tags = "问题管理")
    public RestResult<RespondentDetailVO> queryRespondentDetail(@PathVariable Long id) {
        return RestResult.ok();
    }

    @GetMapping("/my/eavesdropping-list")
    @ApiOperation(notes = "我的偷听", value = "我的偷听", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryMyEavesdroppingList() {
        return RestResult.ok();
    }

    @GetMapping("/my/question-list/{pageNo}/{pageSize}")
    @ApiOperation(notes = "我的问题", value = "我的问题", tags = "问题管理")
    public RestResult<Page<QuestionModel>> queryMyQuestionList(@PathVariable Integer pageNo, @PathVariable Integer pageSize,
                                                               HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
//        根据当前用户的userId查询问题
        try {
//
            QuestionQueryVO questionQueryVO = new QuestionQueryVO();
            questionQueryVO.setAskerId(userId);
            questionQueryVO.setPageNo(pageNo);
            questionQueryVO.setPageSize(pageSize);


            Page<QuestionModel> page = qaService.getUserQuestionsByPage(questionQueryVO);
            return RestResult.ok(page);
        } catch (Exception e) {
            return RestResult.fail(e.getMessage());
        }
    }

    @GetMapping("/my/answer-list")
    @ApiOperation(notes = "我的回答", value = "我的回答", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryMyAnswerList() {
        return RestResult.ok();
    }

    @GetMapping("/eavesdropping/{id}")
    @ApiOperation(notes = "偷听问题,若该回答还有音频版本，则调用该接口后，再调用统一下载接口对应音频文件实现在线播放",
            value = "偷听问题", tags = "问题管理")
    public RestResult<QuestionVO> eavesdroppingQuestion(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        //扣钱，变更问题对该用户状态(不可见->可见)，返回回答内容
        //若该回答还有音频版本，则调用该接口后，再调用统一下载接口对应音频文件实现在线播放
        return RestResult.ok();
    }

    @PostMapping("/question/{askUserId}")
    @ApiOperation(notes = "提问问题", value = "提问问题", tags = "问题管理")
    public RestResult<Map<String, Question>> askQuestion(@PathVariable Long responderId, @PathVariable String content,
                                       @PathVariable BigDecimal reward, HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);

//       所有身份的用户都可以发布问题，但是提问者回答者id不能相同
        if (!userId.equals(responderId)) {
            // paid默认为false
            // answerId默认为-1, 表示未回答，回答时更新
            Question question = new Question(userId, responderId, content, reward, false, -1, 0, 0);
            try {
                qaService.createQuestion(question);
            } catch (Exception e) {
                return RestResult.fail(e.getMessage());
            }
            Map<String, Question> questionMap = new HashMap<>();
            questionMap.put("question", question);
            return RestResult.ok(questionMap);
        } else {
            return RestResult.fail("writer cannot write question to himself");
        }
    }

    @DeleteMapping("/question/{questionId}")
    @ApiOperation(notes = "删除问题", value = "删除问题", tags = "问题管理")
    public RestResult<Boolean> deleteQuestion(@PathVariable Integer questionId, HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        boolean deleted;
//        删除问题
        try {
            deleted = qaService.deleteQuestion(userId, questionId);
        } catch (Exception e) {
            return RestResult.fail(e.getMessage());
        }
        return RestResult.ok(deleted);
    }
}
