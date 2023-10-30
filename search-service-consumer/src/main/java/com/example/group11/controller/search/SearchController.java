package com.example.group11.controller.search;


import com.example.group11.commons.utils.RestResult;
import com.example.group11.entity.es.QaES;
import com.example.group11.entity.es.UserES;
import com.example.group11.service.search.SearchService;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * FileName: SearchController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/11 20:51
 */
@RestController
@RequestMapping("/sys/search")
public class SearchController {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.search.SearchService.class)
    private SearchService searchService;

    @GetMapping("/user")
    @ApiOperation(value = "根据关键词搜索用户", tags = "搜索")
    public RestResult<Page<UserES>> searchUser(Integer page, Integer size, String description) {
        return RestResult.ok(searchService.queryUser(page, size, description));
    }

    @GetMapping("/questions")
    @ApiOperation(value = "根据关键词搜索问题", tags = "搜索")
    public RestResult<Page<QaES>> searchQuestions(Integer page, Integer size,
                                                  String questionContent, String answerContent, String category) {
        return RestResult.ok(searchService.queryQa(page, size, questionContent, answerContent, category));
    }
}
