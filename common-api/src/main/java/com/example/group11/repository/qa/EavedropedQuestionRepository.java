package com.example.group11.repository.qa;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.sql.EavedropedQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * FileName: EavedropedQuestionRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/26 19:13
 */
public interface EavedropedQuestionRepository extends BaseRepository<EavedropedQuestion, Integer> {

    Optional<EavedropedQuestion> findByQuestionIdAndUserId(Integer questionId, Long userId);

    Page<EavedropedQuestion> findAllByUserId(Long userId, Pageable pageable);
}
