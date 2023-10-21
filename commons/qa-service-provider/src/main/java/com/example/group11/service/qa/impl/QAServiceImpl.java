package com.example.group11.service.qa.impl;

import com.example.group11.commons.utils.Group11Exception;
import com.example.group11.entity.Question;
import com.example.group11.repository.qa.QuestionRepository;
import com.example.group11.service.qa.QAService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * FileName: QAService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description QA Service
 * @Date 2023/10/14 19:23
 */
@DubboService(version="1.0.0", interfaceClass = com.example.group11.service.qa.QAService.class)
public class QAServiceImpl implements QAService {

    @Autowired
    QuestionRepository questionRepository;

    /**
     * @description:
     * @author: liuzijian
     * @date: 2023/10/19 16:55
     * @param: []
     * @return: void
     **/
    public Integer createQuestion(Question question) throws Group11Exception {
//        paid默认为false
//        answerId默认为-1, 表示未回答
//

        questionRepository.save(question);
        return question.getId();
    }
}
