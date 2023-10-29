package com.example.group11.service.user;

import com.example.group11.commons.utils.BaseService;
import com.example.group11.model.UserModel;
import com.example.group11.vo.query.UserQueryVO;
import org.springframework.data.domain.Page;


import java.util.List;


public interface UserService extends BaseService<UserModel, Long> {

    UserModel queryUserByLoginName(String loginName);

    Long queryUserIdByLoginName(String loginName);

    List<UserModel> queryFanListByUserId(Long userId, UserQueryVO params);

    List<UserModel> queryFollowListByUserId(Long userId, UserQueryVO params);

    Page<UserModel> queryUserListByPage(UserQueryVO params);


}
