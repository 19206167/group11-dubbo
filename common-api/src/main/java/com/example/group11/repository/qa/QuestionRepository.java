package com.example.group11.repository.qa;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.Question;
import com.example.group11.model.QuestionModel;
import com.example.group11.repository.custom.QuestionRepositoryCustom;
import org.apache.dubbo.common.utils.Page;

import org.springframework.data.domain.Pageable;


/**
 * FileName: QuestionRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 21:27
 */
public interface QuestionRepository extends BaseRepository<Question, Integer> {

}
