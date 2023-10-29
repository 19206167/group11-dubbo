package com.example.group11.repository.qa;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.sql.Answer;
import com.example.group11.model.AnswerModel;

/**
 * FileName: AnswerRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 21:27
 */
public interface AnswerRepository extends BaseRepository<Answer, Integer> {
    Answer findByQuestionId(Integer questionId);
}
