package com.example.group11.service.qa;

import com.example.group11.commons.utils.BaseService;
import com.example.group11.entity.Question;
import com.example.group11.model.QuestionModel;
import com.example.group11.vo.query.QuestionQueryVO;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * FileName: QAService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 17:47
 */
public interface QAService extends BaseService<QuestionModel, Integer> {
//    添加问题
    void createQuestion(Question question);

//    删除问题
    boolean deleteQuestion(Long userId, Integer questionId);

//    查看用户问题列表
    Page<QuestionModel> getUserQuestionsByPage(QuestionQueryVO questionQueryVO);

//    回答问题
    void answerQuestionText(Long responderId, Integer questionId, String answerContent);
}
