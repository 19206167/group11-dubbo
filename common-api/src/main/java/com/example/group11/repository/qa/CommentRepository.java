package com.example.group11.repository.qa;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.sql.Comment;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

/**
 * FileName: CommentRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 21:31
 */
public interface CommentRepository extends BaseRepository<Comment, Integer> {

    @Modifying
    @Transactional
    Integer deleteByIdAndUserIdAndQuestionId(Integer id, Long userId, Integer questionId);
}
