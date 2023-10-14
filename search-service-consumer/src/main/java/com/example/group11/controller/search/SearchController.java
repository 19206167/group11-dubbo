package com.example.group11.controller.search;


import com.example.group11.commons.utils.RestResult;
import com.example.group11.vo.QuestionVO;
import com.example.group11.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * FileName: SearchController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/11 20:51
 */
@RestController
public class SearchController {
    @GetMapping("search/user/{content}")
    @ApiOperation(value = "根据关键词搜索用户", tags = "搜索")
    public RestResult<List<UserVO>> searchUser(@PathVariable String content) {
        return RestResult.ok();
    }

    @GetMapping("search/questions/{content}")
    @ApiOperation(value = "根据关键词搜索问题", tags = "搜索")
    public RestResult<List<QuestionVO>> searchQuestions(@PathVariable String content) {
        return RestResult.ok();
    }

}
