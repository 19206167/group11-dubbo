package com.example.group11.service.user.impl;

import com.example.group11.commons.utils.BaseServiceImpl;
import com.example.group11.commons.utils.CheckUtil;
import com.example.group11.commons.utils.OrikaUtil;
import com.example.group11.entity.sql.User;
import com.example.group11.enums.PageEnum;
import com.example.group11.model.UserModel;
import com.example.group11.repository.user.UserRepository;
import com.example.group11.service.user.UserService;
import com.example.group11.vo.query.UserQueryVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: UserServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description UserServiceImpl
 * @Date 2023/10/14 19:20
 */
@DubboService(version = "1.0.0", interfaceClass = com.example.group11.service.user.UserService.class)
public class UserServiceImpl extends BaseServiceImpl<UserModel, User, Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected Class<UserModel> getModelType() {
        return UserModel.class;
    }

    @Override
    protected Class<User> getEntityType() {
        return User.class;
    }


    @Override
    public UserModel queryUserByLoginName(String loginName) {
        User user = userRepository.findByLoginNameAndDeleted(loginName, false);
        return mapBean(user, UserModel.class);
    }

    @Override
    public Long queryUserIdByLoginName(String loginName) {
        UserModel userModel = this.queryUserByLoginName(loginName);
        if (CheckUtil.isEmpty(userModel)) {
            return null;
        }
        return userModel.getId();
    }

    @Override
    public List<UserModel> queryFanListByUserId(Long userId, UserQueryVO params) {
        List<User> userList = userRepository.queryFanListByUserId(userId, params);
        return OrikaUtil.mapAsList(userList, UserModel.class);
    }

    @Override
    public Page<UserModel> queryUserListByPage(UserQueryVO params) {
        Integer pageNo = CheckUtil.isNotEmpty(params.getPageNo()) ? params.getPageNo() : PageEnum.DEFAULT_PAGE_NO.getKey();
        Integer pageSize = CheckUtil.isNotEmpty(params.getPageSize()) ? params.getPageSize() : PageEnum.DEFAULT_PAGE_SIZE.getKey();

        Specification<User> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (CheckUtil.isNotEmpty(params.getId())) {
                predicates.add(builder.equal(root.get("id"), params.getId()));
            }
            if (CheckUtil.isNotEmpty(params.getLoginName())) {
                predicates.add(builder.like(root.get("loginName"), "%" + params.getLoginName() + "%"));
            }
            if (CheckUtil.isNotEmpty(params.getUserName())) {
                predicates.add(builder.like(root.get("userName"), "%" + params.getUserName() + "%"));
            }
            if (CheckUtil.isNotEmpty(params.getEmail())) {
                predicates.add(builder.like(root.get("email"), "%" + params.getEmail() + "%"));
            }
            if (CheckUtil.isNotEmpty(params.getPhone())) {
                predicates.add(builder.like(root.get("phone"), "%" + params.getPhone() + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> UserPage = userRepository.findAll(spec, pageable);
        List<UserModel> userModelList = OrikaUtil.mapAsList(UserPage.getContent(), UserModel.class);
        return new PageImpl<>(userModelList, pageable, UserPage.getTotalElements());
    }

}
