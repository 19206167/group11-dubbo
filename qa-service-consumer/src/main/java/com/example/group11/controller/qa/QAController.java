package com.example.group11.controller.qa;


import com.example.group11.commons.utils.RestResult;
import com.example.group11.vo.QuestionDetailVO;
import com.example.group11.vo.QuestionVO;
import com.example.group11.vo.RespondentDetailVO;
import com.example.group11.vo.RespondentVO;
import com.example.group11.vo.query.QuestionQueryVO;
import com.example.group11.vo.query.RespondentQueryVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/qa/")
public class QAController {

    @GetMapping("all/question-list")
    @ApiOperation(notes = "根据多条件查询全部回答的分页列表", value = "根据多条件查询全部回答的分页列表", tags = "问题管理")
    public RestResult<Page<QuestionVO>> queryAllQuestionList(QuestionQueryVO params, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }

    @GetMapping("all/respondent-list")
    @ApiOperation(notes = "根据多条件查询全部答主的分页列表", value = "根据多条件查询全部答主的分页列表", tags = "问题管理")
    public RestResult<Page<RespondentVO>> queryAllRespondentList(RespondentQueryVO params, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }

    @GetMapping("hot/question-list")
    @ApiOperation(notes = "首页热门回答列表", value = "首页热门回答列表", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryHotQuestionList() {
        return RestResult.ok();
    }

    @GetMapping("hot/respondent-list")
    @ApiOperation(notes = "首页热门答主列表", value = "首页热门答主列表", tags = "问题管理")
    public RestResult<List<RespondentVO>> queryHotRespondentList() {
        return RestResult.ok();
    }

    @GetMapping("question/{id}")
    @ApiOperation(notes = "问答详情", value = "问答详情", tags = "问题管理")
    public RestResult<QuestionDetailVO> queryQuestionDetail(@PathVariable Long id) {
        return RestResult.ok();
    }

    @GetMapping("respondent/{id}")
    @ApiOperation(notes = "答主详情", value = "答主详情", tags = "问题管理")
    public RestResult<RespondentDetailVO> queryRespondentDetail(@PathVariable Long id) {
        return RestResult.ok();
    }

    @GetMapping("my/eavesdropping-list")
    @ApiOperation(notes = "我的偷听", value = "我的偷听", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryMyEavesdroppingList() {
        return RestResult.ok();
    }

    @GetMapping("my/question-list")
    @ApiOperation(notes = "我的问题", value = "我的问题", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryMyQuestionList() {
        return RestResult.ok();
    }

    @GetMapping("my/answer-list")
    @ApiOperation(notes = "我的回答", value = "我的回答", tags = "问题管理")
    public RestResult<List<QuestionVO>> queryMyAnswerList() {
        return RestResult.ok();
    }

    @GetMapping("eavesdropping/{id}")
    @ApiOperation(notes = "偷听问题,若该回答还有音频版本，则调用该接口后，再调用统一下载接口对应音频文件实现在线播放",
            value = "偷听问题", tags = "问题管理")
    public RestResult<QuestionVO> eavesdroppingQuestion(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        //扣钱，变更问题对该用户状态(不可见->可见)，返回回答内容
        //若该回答还有音频版本，则调用该接口后，再调用统一下载接口对应音频文件实现在线播放
        return RestResult.ok();
    }

    @PostMapping("ask")
    @ApiOperation(notes = "提问问题", value = "提问问题", tags = "问题管理")
    public RestResult<QuestionVO> askQuestion(@RequestBody QuestionVO questionVO, HttpServletRequest httpServletRequest) {
        //向kafka发送
        return RestResult.ok();
    }

    @PostMapping("answer")
    @ApiOperation(notes = "回答问题", value = "回答问题", tags = "问题管理")
    public RestResult<QuestionVO> answerQuestion(@RequestBody QuestionVO questionVO, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }


}
