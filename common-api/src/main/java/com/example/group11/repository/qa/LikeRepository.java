package com.example.group11.repository.qa;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.sql.Like;

import java.util.Optional;

/**
 * FileName: LikeRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 21:28
 */
public interface LikeRepository extends BaseRepository<Like, Integer> {
    boolean deleteByUserIdAndQuestionId(Long userId, Integer questionId);

    Optional<Like> findByUserIdAndQuestionId(Long userId, Integer questionId);
}
