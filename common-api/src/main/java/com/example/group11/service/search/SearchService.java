package com.example.group11.service.search;


import com.example.group11.entity.es.QaES;
import com.example.group11.entity.es.UserES;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * FileName: SearchService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 17:57
 */
public interface SearchService {

    Boolean saveUser(UserES... userES);

    Boolean saveQa(QaES... qaES);

    Boolean deleteUser(String id);

    UserES getUserById(String id);

    List<UserES> getAllUser();

    Page<UserES> queryUser(Integer page, Integer size, String keyword);

    Page<QaES> queryQa(Integer page, Integer size, String questionContent, String answerContent);

}
