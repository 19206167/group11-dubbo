package com.example.group11.controller.user;


import com.example.group11.commons.utils.*;
import com.example.group11.entity.es.UserES;
import com.example.group11.enums.RoleEnum;
import com.example.group11.model.UserModel;
import com.example.group11.service.search.SearchService;
import com.example.group11.service.user.UserService;
import com.example.group11.vo.UserToRespondentVO;
import com.example.group11.vo.UserVO;
import com.example.group11.vo.query.UserQueryVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.user.UserService.class)
    private UserService userService;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.search.SearchService.class, check = false)
    private SearchService searchService;


    @GetMapping("/sys/user/test")
    @ApiOperation(notes = "test", value = "test", tags = "用户管理")
    public RestResult<String> test() {
        return RestResult.ok("1111111");
    }


    @PostMapping("/sys/user/login")
    @ApiOperation(notes = "用户登录获取token", value = "用户登录获取token", tags = "用户管理")
    public RestResult login(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        UserModel userModel = userService.queryUserByLoginName(loginName);
        if(CheckUtil.isEmpty(userModel)) {
            return RestResult.fail("该用户名对应的用户不存在");
        }
        log.info(userModel.toString());
        if (userModel.getPassword().equals(ShiroUtil.sha256(password, userModel.getSalt()))) {
            return RestResult.ok(JWTUtil.sign(userModel.getLoginName(), userModel.getId(), userModel.getRole()));
        } else {
            return RestResult.fail("用户名密码错误");
        }
    }

    @GetMapping("/sys/user/loginname/{loginName}")
    @ApiOperation(notes = "根据用户名查询用户信息", value = "根据用户名查询用户信息", tags = "用户管理")
    public RestResult<UserVO> queryUserByLoginName(@PathVariable String loginName) {
        log.info(loginName);
//        response.setHeader("Access-Control-Allow-Origin", "*");
////* 代办允许所有方法
//        response.setHeader("Access-Control-Allow-Methods", "*");
//// Access-Control-Max-Age 用于 CORS 相关配置的缓存
//        response.setHeader("Access-Control-Max-Age", "3600");
//// 提示OPTIONS预检时，后端需要设置的两个常用自定义头
//        response.setHeader("Access-Control-Allow-Headers", "*");
//// 允许前端带认证cookie：启用此项后，上面的域名不能为'*'，必须指定具体的域名，否则浏览器会提示
//        response.setHeader("Access-Control-Allow-Credentials", "true");
        UserModel userModel = userService.queryUserByLoginName(loginName);
        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }

    @GetMapping("/sys/user/id/{id}")
    @ApiOperation(notes = "根据用户id查询用户信息", value = "根据用户id查询用户信息", tags = "用户管理")
    public RestResult<UserVO> queryById(@PathVariable Long id) {
        UserModel userModel = userService.findById(id);
        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }

    @GetMapping("/sys/user/me")
    @ApiOperation(notes = "查看当前登录用户信息", value = "查看当前登录用户信息", tags = "用户管理")
    public RestResult<UserVO> queryCurrentUser(HttpServletRequest httpServletRequest) {
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        log.info("[queryCurrentUser],userId={}", userId);
        UserModel userModel = userService.findById(userId);
        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }

    @PostMapping("/sys/user/me/respondent")
    @ApiOperation(notes = "成为答主,调用该接口前，首先调用统一文件上传接口上传语音自我介绍文件，并得到返回的语音自我介绍url", value = "成为答主", tags = "用户管理")
//首先调用统一文件上传接口上传语音自我介绍文件，并得到返回的语音自我介绍url
    public RestResult<UserVO> toRespondent(@RequestBody UserToRespondentVO userToRespondentVO, HttpServletRequest httpServletRequest) {
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        log.info("[toRespondent],userId={}", userId);

        UserModel userModel = userService.findById(userId);
        if (!RoleEnum.READER.getKey().equals(userModel.getRole())) {
            return RestResult.fail("只有读者才能成为作者！");
        }
        OrikaUtil.map(userToRespondentVO, userModel);
        userModel.setRole(RoleEnum.WRITER.getKey());
        userService.updateById(userModel);

        UserES userES = OrikaUtil.map(userModel, UserES.class);
        searchService.saveUser(userES);
        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }


    @PostMapping("/exapi/sys/user/id")
    @ApiOperation(notes = "提交新注册用户信息，调用该接口前，首先调用统一文件上传接口上传用户头像文件，并得到返回的用户头像url", value = "提交新注册用户信息", tags = "用户管理")
    //首先调用统一文件上传接口上传用户头像文件，并得到返回的用户头像url
    public RestResult<UserVO> insertUser(@RequestBody UserVO form) {
        log.info("[insertUser],form={}", form);
        if (CheckUtil.isEmpty(form.getLoginName()) || CheckUtil.isEmpty(form.getUserName()) ||
                CheckUtil.isEmpty(form.getPassword())) {
            return RestResult.fail("请填写登录名，用户名和密码");
        }
        if (CheckUtil.isNotEmpty(userService.queryUserIdByLoginName(form.getLoginName()))) {
            return RestResult.fail("该loginName已存在");
        }
        String salt = RandomStringUtils.randomAlphanumeric(20);
        form.setSalt(salt);
        form.setPassword(ShiroUtil.sha256(form.getPassword(), salt));
        UserModel model = OrikaUtil.map(form, UserModel.class);
        Long id = userService.insertOne(model);
        UserModel userModel = userService.findById(id);

        UserES userES = OrikaUtil.map(userModel, UserES.class);
        searchService.saveUser(userES);
        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }

    @PutMapping("/sys/user/id/")
    @ApiOperation(notes = "用户修改自己的信息", value = "用户修改自己的信息", tags = "用户管理")
    public RestResult<UserVO> updateUserById(@RequestBody UserVO form, HttpServletRequest httpServletRequest) {
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        log.info("[updateUserById],form={},userId={}", form, userId);
        if (CheckUtil.isEmpty(form.getLoginName()) || CheckUtil.isEmpty(form.getUserName()) ||
                CheckUtil.isEmpty(form.getPassword())) {
            return RestResult.fail("请填写登录名，用户名和密码");
        }
        if (CheckUtil.isNotEmpty(userService.queryUserIdByLoginName(form.getLoginName())) &&
                !form.getLoginName().equals(userService.findById(userId).getLoginName())) {
            return RestResult.fail("该loginName已存在");
        }
        form.setId(userId);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        form.setSalt(salt);
        form.setPassword(ShiroUtil.sha256(form.getPassword(), salt));
        UserModel model = OrikaUtil.map(form, UserModel.class);
        userService.updateById(model);
        UserModel userModel = userService.findById(userId);

        UserES userES = OrikaUtil.map(userModel, UserES.class);
        searchService.saveUser(userES);
        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }

    @GetMapping("/sys/user")
    @ApiOperation(notes = "根据多条件分页查询用户列表", value = "根据多条件分页查询用户列表", tags = "用户管理")
    public RestResult<Page<UserVO>> queryUserListByPage(UserQueryVO params) {
        log.info("[queryUserList] params={}", params);
        Page<UserModel> modelPage = userService.queryUserListByPage(params);
        List<UserVO> userVOList = OrikaUtil.mapAsList(modelPage.getContent(), UserVO.class);
        return RestResult.ok(new PageImpl<>(userVOList, modelPage.getPageable(), modelPage.getTotalElements()));
    }


}
