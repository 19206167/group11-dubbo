package com.example.group11.controller.user;


import com.example.group11.commons.utils.CheckUtil;
import com.example.group11.commons.utils.JWTUtil;
import com.example.group11.commons.utils.OrikaUtil;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.model.FollowModel;
import com.example.group11.model.UserModel;
import com.example.group11.service.user.FollowService;
import com.example.group11.service.user.UserService;
import com.example.group11.vo.UserVO;
import com.example.group11.vo.query.UserQueryVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/follow/")
public class FollowController {

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.user.UserService.class)
    private UserService userService;

    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.user.FollowService.class)
    private FollowService followService;

    @GetMapping("me/fan-list")
    @ApiOperation(notes = "根据多条件查询当前登录用户的粉丝分页列表", value = "根据多条件查询当前登录用户的粉丝分页列表", tags = "关注管理")
    public RestResult<List<UserVO>> queryMyFanList(UserQueryVO params, HttpServletRequest httpServletRequest) {
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        log.info("[queryMyFanList],userId={}", userId);

        List<UserModel> userModelList = OrikaUtil.mapAsList(userService.queryFanListByUserId(userId, params), UserModel.class);
        return RestResult.ok(OrikaUtil.mapAsList(userModelList, UserVO.class));
    }

    @GetMapping("me/following-list")
    @ApiOperation(notes = "根据多条件查询当前登录用户的关注分页列表", value = "根据多条件查询当前登录用户的关注分页列表", tags = "关注管理")
    public RestResult<List<UserVO>> queryMyFollowingList(UserQueryVO params, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }

    @GetMapping("{id}/fan-list")
    @ApiOperation(notes = "根据多条件查询目标用户的粉丝分页列表", value = "根据多条件查询目标用户的粉丝分页列表", tags = "关注管理")
    public RestResult<List<UserVO>> queryUserFanList(@PathVariable Long id, UserQueryVO params, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }

    @GetMapping("{id}/following-list")
    @ApiOperation(notes = "根据多条件查询目标用户的关注分页列表", value = "根据多条件查询目标用户的关注分页列表", tags = "关注管理")
    public RestResult<List<UserVO>> queryUserFollowingList(@PathVariable Long id, UserQueryVO params, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }

    @PostMapping("add/{followedUserId}")
    @ApiOperation(notes = "关注目标用户", value = "关注目标用户", tags = "关注管理")
    public RestResult<UserVO> addFollowingUser(@PathVariable Long followedUserId, HttpServletRequest httpServletRequest) {
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);
        log.info("[addFollowing],followedUserId={},userId={}", followedUserId, userId);

        UserModel userModel = userService.findById(followedUserId);
        if (CheckUtil.isEmpty(userModel)) {
            return RestResult.fail("目标用户不存在");
        }
        FollowModel followModel = new FollowModel();
        followModel.setBeFollowedUserId(followedUserId);
        followModel.setFollowingUserId(userId);
        followService.insertOne(followModel);

        return RestResult.ok(OrikaUtil.map(userModel, UserVO.class));
    }

    @PostMapping("cancel/{followedUserId}")
    @ApiOperation(notes = "取消关注目标用户", value = "取消关注目标用户", tags = "关注管理")
    public RestResult<UserVO> cancelFollowingUser(@PathVariable Long followedUserId, HttpServletRequest httpServletRequest) {
        return RestResult.ok();
    }

}
