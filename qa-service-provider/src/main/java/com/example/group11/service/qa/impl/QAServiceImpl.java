package com.example.group11.service.qa.impl;

import com.example.group11.commons.utils.BaseServiceImpl;
import com.example.group11.commons.utils.CheckUtil;
import com.example.group11.commons.utils.ErrorCode;
import com.example.group11.commons.utils.Group11Exception;
import com.example.group11.entity.sql.Answer;
import com.example.group11.entity.sql.Question;
import com.example.group11.entity.sql.User;
import com.example.group11.enums.PageEnum;
import com.example.group11.model.QuestionModel;
import com.example.group11.repository.qa.AnswerRepository;
import com.example.group11.repository.qa.QuestionRepository;
import com.example.group11.repository.user.UserRepository;
import com.example.group11.service.qa.QAService;
import com.example.group11.vo.query.QuestionQueryVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.Optional;

/**
 * FileName: QAService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description QA Service
 * @Date 2023/10/14 19:23
 */
@DubboService(version="1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
public class QAServiceImpl extends BaseServiceImpl<QuestionModel, Question, Integer> implements QAService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    protected Class<QuestionModel> getModelType() {
        return QuestionModel.class;
    }

    @Override
    protected Class<Question> getEntityType() {
        return Question.class;
    }


    /**
     * @description: 新增一个问题
     * @author: liuzijian
     * @date: 2023/10/20 16:52
     * @param: [question]
     **/

    @Override
    public void createQuestion(Question question) throws Group11Exception {
        Long responderId = question.getResponderId();
        User user = userRepository.getOne(responderId);

//        检测被提问者是否是回答者
        if (user.getRole() != 1) {
            throw new Group11Exception(ErrorCode.USER_ROLE_ERROR, "被提问者不是写作者，提问者只能向写作者提问。");
        }

//        向数据库中添加数据
        questionRepository.save(question);
    }

    /*
     * @description: 根据id删除问题
     * @author: liuzijian
     * @date: 2023/10/20 17:11
     * @param: userId, questionId
     * @return:
     **/
    @Override
    public boolean deleteQuestion(Long userId, Integer questionId) throws Group11Exception {
        Optional<Question> question = questionRepository.findById(questionId);
//        如果问题不存在，抛出异常
        if (!question.isPresent()) {
            throw new Group11Exception(ErrorCode.EMPTY_RESULT, "问题不存在");
        }
        if (question.get().getAskerId().equals(userId)) {
//            将question逻辑删除
            questionRepository.delete(question.get());
            return true;
        } else {
//            如果问题的askerId不等于用户id, 抛出异常
            throw new Group11Exception(ErrorCode.EMPTY_RESULT, "问题不属于该用户，没有删除权限");
        }
    }

    /*
     * @description: 根据提问者id查询问题列表并返回
     * @author: liuzijian
     * @date: 2023/10/20 17:36
     * @param: [askerId]
     * @return: java.util.List<com.example.group11.entity.sql.Question>
     **/
    @Override
    public Page<QuestionModel> getUserQuestionsByPage(QuestionQueryVO questionQueryVO) {
//        得到问题的页码和总页数以及遍历方式
        Integer pageNo = CheckUtil.isNotEmpty(questionQueryVO.getPageNo()) ? questionQueryVO.getPageNo() : PageEnum.DEFAULT_PAGE_NO.getKey();
        Integer pageSize = CheckUtil.isNotEmpty(questionQueryVO.getPageSize()) ? questionQueryVO.getPageSize() : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        QuestionModel questionModel = new QuestionModel();
        if (CheckUtil.isEmpty(questionModel.getAskerId())) {
            questionModel.setAskerId(questionQueryVO.getAskerId());
        } else {
            throw new Group11Exception(ErrorCode.EMPTY_PARAM);
        }

        return findPageByExample(questionModel, pageable);
    }

    /**
     * @description: 回答问题（文本回答）
     * @author: liuzijian
     * @date: 2023/10/20 20:38
     * @param: [responderId, questionId, answerContent]
     * @return: void
     **/
    @Override
    public void answerQuestionText(Long responderId, Integer questionId, String answerContent) throws Group11Exception{

        Optional<Question> question = questionRepository.findById(questionId);

//        验证问题存在，且问题回答者与当前用户匹配
        if (question.isPresent() ) {
            if (question.get().getResponderId().equals(responderId)) {
//                添加回答，并且更新问题中的答案id
                Answer answer = new Answer(questionId, answerContent, 0);
                answerRepository.save(answer);
                question.get().setAnswerId(answer.getId());
            } else {
                throw new Group11Exception(ErrorCode.USER_ROLE_ERROR);
            }
        } else {
            throw new Group11Exception(ErrorCode.EMPTY_RESULT);
        }
    }


}
