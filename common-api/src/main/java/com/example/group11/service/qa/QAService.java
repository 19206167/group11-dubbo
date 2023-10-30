package com.example.group11.service.qa;

import com.example.group11.commons.utils.BaseService;
import com.example.group11.entity.sql.Comment;
import com.example.group11.entity.sql.Question;
import com.example.group11.model.AnswerModel;
import com.example.group11.model.QuestionModel;
import com.example.group11.vo.query.QuestionQueryVO;
import io.swagger.models.auth.In;
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

//  按id查找问题
    QuestionModel queryQuestionById(int questionId);


//    查看用户问题列表
    Page<QuestionModel> getUserQuestionsByPage(QuestionQueryVO questionQueryVO);

//    根据问题Id获取答案
    AnswerModel getAnswerByQuestionId(Integer questionId);

//    文字回答问题
//    void answerQuestionText(Long responderId, Integer questionId, String answerContent);

//    语音回答问题, 也可以添加文字描述
//    需要先经过file Module得到url
//    type0 是语音回答，1 是文字回答
    AnswerModel answerQuestion(AnswerModel answerModel);

//    修改文字回答问题 answerId及 回答者id
    AnswerModel updateTextQuestionAnswer(Long userId, Integer answerId, List<String> url, String answerContent);

//    根据答案id查询答案
    AnswerModel findAnswerModelById(Integer answerId);

//    判断用户能否直接得到问题答案
    boolean checkWhetherGetAnswer(Long userId, Integer questionId);

//    判断是否偷听
    boolean checkUserEavesdropQuestion(Long userId, Integer questionId);

//    偷听问题
    boolean eavesdropQuestionById(Long userId, Integer questionId, Integer transactionId);

//    查看用户偷听的问题
    Page<Question> checkEavesdropQuestionByUserIdByPage(Long userId, Integer pageNo, Integer pageSize);

//    查看待回答的问题, 没回答问题id等于0
    Page<Question> checkUnansweredQuestionByResponderIdByPage(Long responderId, Integer pageNo, Integer pageSize);

//    查看回答者已回答问题, responderId 且 answerId != 0
    Page<Question> getResponderAnsweredQuestionsByPage(Long responderId, Integer pageNo, Integer pageSize);

//    点赞
    boolean like(Long userId, Integer questionId);

//    取消点赞
    boolean cancelLike(Long userId, Integer questionId);

//    获取某问题点赞数
    Integer getLikeNum(Integer questionId);

//    评论
    boolean comment(Long userId, Integer questionId, String content);

//    删除评论
    boolean deleteComment(Long userId, Integer questionId, Integer commentId);

//    获取某问题评论数
    Integer getCommentNum(Integer questionId);

//    获取某个问题的所有评论
    Page<Comment> getCommentByQuestionByPage(Integer questionId, Integer pageNo, Integer pageSize);

//    Integer insertAnswer(AnswerModel answerModel);
}
