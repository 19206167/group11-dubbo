package com.example.group11.service.user;

import com.example.group11.commons.utils.BaseService;
import com.example.group11.model.UserModel;
import com.example.group11.vo.query.UserQueryVO;


import java.util.List;


public interface UserService extends BaseService<UserModel, Long> {

    UserModel queryUserByLoginName(String loginName);

    Long queryUserIdByLoginName(String loginName);

    List<UserModel> queryFanListByUserId(Long useId, UserQueryVO params);


}
