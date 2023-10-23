package com.example.group11.repository.impl.qa;

import com.example.group11.repository.custom.QuestionRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * FileName: QuestionRepositoryImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description QuestionRepositoryImpl
 * @Date 2023/10/20 17:05
 */

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


//    @Override
//    public Page<QuestionModel> findAllQuestionsByAskerId(QuestionQueryVO params) {
//        Integer pageNo = CheckUtil.isNotEmpty(params.getPageNo()) ? params.getPageNo() : PageEnum.DEFAULT_PAGE_NO.getKey();
//        Integer pageSize = CheckUtil.isNotEmpty(params.getPageSize()) ? params.getPageSize() : PageEnum.DEFAULT_PAGE_SIZE.getKey();
//
//        String jpql = "select question from Question question where question.askerId = :p1 and question.deleted = false order by question.createTime desc";
//
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter("p1", params.getAskerId());
//
//        query.setFirstResult(pageNo * pageSize);
//        query.setMaxResults(pageSize);
//
//        return (Page<QuestionModel>) query;
//    }
}
