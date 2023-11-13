package com.example.group11.service.qa.impl;

import com.example.group11.commons.utils.*;
import com.example.group11.entity.sql.*;
import com.example.group11.enums.PageEnum;
import com.example.group11.model.AnswerModel;
import com.example.group11.model.QuestionModel;
import com.example.group11.repository.qa.*;
import com.example.group11.repository.user.UserRepository;
import com.example.group11.service.qa.QAService;
import com.example.group11.service.transaction.TransactionService;
import com.example.group11.vo.query.QuestionQueryVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

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
    EavedropedQuestionRepository eavedropedQuestionRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @DubboReference(version="1.0.0", interfaceClass = com.example.group11.service.transaction.TransactionService.class, check = false)
    TransactionService transactionService;

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
    @Transactional
    public void createQuestion(Question question) throws Group11Exception {
        Long responderId = question.getResponderId();
        Optional<User> user = userRepository.findById(responderId);

        if(user.isPresent()) {
            // 检测被提问者是否是回答者
            if (user.get().getRole() != 1) {
                throw new Group11Exception(ErrorCode.USER_ROLE_ERROR, "被提问者不是写作者，提问者只能向写作者提问。");
            }

            // 账户扣款
            transactionService.payForCreateQuestion(question.getAskerId(), question.getAskerId(), question.getReward());
            question.setPaid(true);

            // 向数据库中添加数据
            questionRepository.save(question);
        }
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

    @Override
    public Page<Question> getHottestQuestionsByPage(Integer pageNo, Integer pageSize) {
        pageNo = CheckUtil.isNotEmpty(pageNo) ? pageNo : PageEnum.DEFAULT_PAGE_NO.getKey();
        pageSize = CheckUtil.isNotEmpty(pageSize) ? pageSize : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "likeNum", "commmentNum", "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return questionRepository.findAll(pageable);
    }


    public QuestionModel queryQuestionById(int questionId){
        Question question = questionRepository.findById(questionId);
        return mapBean(question, QuestionModel.class);
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

//    根据问题id查到对应的答案
    @Override
    public Answer getAnswerByQuestionId(Integer questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    /**
     * @description: 回答问题（文本回答）
     * @author: liuzijian
     * @date: 2023/10/20 20:38
     * @param: [responderId, questionId, answerContent]
     * @return: void
     **/
    @Override
    @Transactional
    public Answer answerQuestion(AnswerModel answerModel) throws Group11Exception{

        Optional<Question> question = questionRepository.findById(answerModel.getQuestionId());
        Answer answer;

//        验证问题存在，且问题回答者与当前用户匹配
        if (question.isPresent() ) {
            if (question.get().getResponderId().equals(answerModel.getUserId())) {
//                添加回答，并且更新问题中的答案id
                answer = new Answer(answerModel.getQuestionId(), answerModel.getContent(), 0);
                answerRepository.save(answer);
                question.get().setAnswerId(answer.getId());
                questionRepository.save(question.get());
                transactionService.getQuestionReward(question.get().getResponderId(), question.get().getReward());
            } else {
                throw new Group11Exception(ErrorCode.USER_ROLE_ERROR);
            }
        } else {
            throw new Group11Exception(ErrorCode.EMPTY_RESULT);
        }
        return answer;
    }

    @Override
    public Answer updateTextQuestionAnswer(Long userId, Integer answerId, List<String> url, String answerContent) {
        Optional<Answer> answer = answerRepository.findById(answerId);

        if (answer.isPresent()) {
            if (answer.get().getUserId().equals(userId)) {
                answer.get().setContent(answerContent);
                answerRepository.save(answer.get());
            } else {
                throw new Group11Exception(ErrorCode.USER_ROLE_ERROR);
            }
        } else {
            throw new Group11Exception(ErrorCode.EMPTY_RESULT);
        }
        return answer.get();
    }


    @Override
    public Answer findAnswerModelById(Integer answerId) {
        return answerRepository.findById(answerId).get();
    }

    @Override
    public boolean checkWhetherGetAnswer(Long userId, Integer questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.get().getAskerId().equals(userId) || question.get().getResponderId().equals(userId)) {
            return true;
        } else {
            Optional<EavedropedQuestion> eavedropedQuestion = eavedropedQuestionRepository.findByQuestionIdAndUserId(questionId, userId);
            if (eavedropedQuestion.isPresent()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean checkUserEavesdropQuestion(Long userId, Integer questionId) {
        Optional<EavedropedQuestion> eavedrop = eavedropedQuestionRepository.findByQuestionIdAndUserId(questionId, userId);
        log.println(eavedrop.get());
        if (!eavedrop.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public BigDecimal getEavesdropQuestionById(Integer questionId) {
        return questionRepository.findById(questionId).get().getReward().multiply(new BigDecimal(0.1));
    }

    @Override
    @Transactional
    public boolean eavesdropQuestionById(Long userId, Integer questionId, BigDecimal amount) {

        Optional<Question> question = questionRepository.findById(questionId);

        if (question.get().getAnswerId() == -1) {
            throw new Group11Exception(ErrorCode.COMMON_ERROR, "问题没有答案");
        }

        Optional<EavedropedQuestion> eavedrop = eavedropedQuestionRepository.findByQuestionIdAndUserId(questionId, userId);

//        如果当前对象不存在，更新，如果已经存在，插入新的数据
        if (!eavedrop.isPresent()) {
//            偷听问题，按照支付金额的10%扣款
            Long transactionId = transactionService.payForEavesdropping(userId, question.get().getResponderId(),
                    question.get().getReward().multiply(amount));
            EavedropedQuestion eavedropedQuestion = new EavedropedQuestion(userId, questionId, transactionId);
            eavedropedQuestionRepository.save(eavedropedQuestion);
        }
        return true;
    }

//    查看一个用户的所有偷听的问题
    @Override
    public Page<Question> checkEavesdropQuestionByUserIdByPage(Long userId, Integer pageNo, Integer pageSize) {
//        定义pageable
        pageNo = CheckUtil.isNotEmpty(pageNo) ? pageNo : PageEnum.DEFAULT_PAGE_NO.getKey();
        pageSize = CheckUtil.isNotEmpty(pageSize) ? pageSize : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        查找偷听的问题id
        Page<EavedropedQuestion> page = eavedropedQuestionRepository.findAllByUserId(userId, pageable);

        List<EavedropedQuestion> eavedrops = page.getContent();

        List<Integer> questionIDs = eavedrops.stream().map((eavedrop) -> eavedrop.getId()).collect(Collectors.toList());

//        构建specification
//        没测试过，不知道是否正确!!!
        Specification<Question> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            CriteriaBuilder.In<Integer> in = builder.in(root.get("questionId"));
//            添加in 条件
            for (Integer id: questionIDs) {
                in.value(id);
                predicates.add(in);
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };

//        查询到所有的问题并返回
        return questionRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Question> checkUnansweredQuestionByResponderIdByPage(Long responderId, Integer pageNo, Integer pageSize) {
        //        定义pageable
        pageNo = CheckUtil.isNotEmpty(pageNo) ? pageNo : PageEnum.DEFAULT_PAGE_NO.getKey();
        pageSize = CheckUtil.isNotEmpty(pageSize) ? pageSize : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

//        responderId = responderId, answerId = -1;
        Specification<Question> spec = (root, query, builder) ->
                builder.and(builder.equal(root.get("responderId"), responderId), builder.equal(root.get("answerId"), -1));

        return questionRepository.findAll(spec, pageable);
    }

//    查看用户已回答问题
    @Override
    public Page<Question> getResponderAnsweredQuestionsByPage(Long responderId, Integer pageNo, Integer pageSize) {
        //        定义pageable
        pageNo = CheckUtil.isNotEmpty(pageNo) ? pageNo : PageEnum.DEFAULT_PAGE_NO.getKey();
        pageSize = CheckUtil.isNotEmpty(pageSize) ? pageSize : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //        responderId = responderId, answerId != 0;
        Specification<Question> spec = (root, query, builder) ->
                builder.and(builder.equal(root.get("responderId"), responderId), builder.notEqual(root.get("answerId"), -1));

        return questionRepository.findAll(spec, pageable);
    }

//    点赞
    @Override
    public boolean like(Long userId, Integer questionId) {
        if (likeRepository.findByUserIdAndQuestionId(userId, questionId).isPresent()) {
            return false;
        }
        Like like = new Like(userId, questionId);
        likeRepository.save(like);

        Optional<Question> question = questionRepository.findById(questionId);
        question.get().setLikeNum(question.get().getLikeNum() + 1);

        questionRepository.save(question.get());
        return true;
    }

//    取消点赞
    @Override
    public boolean cancelLike(Long userId, Integer questionId) {
        return likeRepository.deleteByUserIdAndQuestionId(userId, questionId);
    }

//    获取点赞数量
    @Override
    public Integer getLikeNum(Integer questionId) {
        return questionRepository.findById(questionId).get().getLikeNum();
    }

    @Override
    public boolean comment(Long userId, Integer questionId, String content) {
        Comment comment  = new Comment(userId, questionId, content);
        commentRepository.save(comment);

        Optional<Question> question = questionRepository.findById(questionId);
        question.get().setCommentNum(question.get().getCommentNum() + 1);

        questionRepository.save(question.get());
        return true;
    }

    @Override
    public boolean deleteComment(Long userId, Integer questionId, Integer commentId) {
        return commentRepository.deleteByIdAndUserIdAndQuestionId(commentId, userId, questionId);
    }

    @Override
    public Integer getCommentNum(Integer questionId) {
        return questionRepository.findById(questionId).get().getCommentNum();
    }

    @Override
    public Page<Comment> getCommentByQuestionByPage(Integer questionId, Integer pageNo, Integer pageSize) {
        //        定义pageable
        pageNo = CheckUtil.isNotEmpty(pageNo) ? pageNo : PageEnum.DEFAULT_PAGE_NO.getKey();
        pageSize = CheckUtil.isNotEmpty(pageSize) ? pageSize : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

//        responderId = responderId, answerId = 0;
        Specification<Comment> spec = (root, query, builder) -> builder.equal(root.get("questionId"), questionId);

        return commentRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Question> queryAllQuestionByCategory(String category, Integer pageNo, Integer pageSize) {
        pageNo = CheckUtil.isNotEmpty(pageNo) ? pageNo : PageEnum.DEFAULT_PAGE_NO.getKey();
        pageSize = CheckUtil.isNotEmpty(pageSize) ? pageSize : PageEnum.DEFAULT_PAGE_SIZE.getKey();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Specification<Question> spec = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (!category.equals("All")){
                predicate = builder.equal(root.get("category"), category);
            }
            return predicate;
        };
        return questionRepository.findAll(spec, pageable);
    }

//    @Override
//    public Integer insertAnswer(AnswerModel answerModel) {
//        Answer answer = OrikaUtil.map(answerModel, Answer.class);
//        answer.setDeleted(false);
//        return answerRepository.save(answer).getId();
//    }
}
