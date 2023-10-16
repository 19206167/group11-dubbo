package com.example.group11.service.user.impl;

import com.example.group11.commons.utils.Group11Exception;
import com.example.group11.model.UserModel;
import com.example.group11.service.user.UserService;
import com.example.group11.vo.query.UserQueryVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

/**
 * FileName: UserServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:20
 */
@DubboService(version="1.0.0", interfaceClass = com.example.group11.service.user.UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public int getOne() {
        return 0;
    }

    @Override
    public UserModel queryUserByLoginName(String loginName) {
        return null;
    }

    @Override
    public Long queryUserIdByLoginName(String loginName) {
        return null;
    }

    @Override
    public List<UserModel> queryFanListByUserId(Long useId, UserQueryVO params) {
        return null;
    }

    @Override
    public UserModel findById(Long id) {
        return null;
    }

    @Override
    public Page<UserModel> findAll(Specification specification, Pageable pageable) {
        return null;
    }

    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public List<UserModel> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public Page<UserModel> findPageByExample(UserModel example, Pageable pageable) {
        return null;
    }

    @Override
    public long countByExample(UserModel example) {
        return 0;
    }

    @Override
    public Long insertOne(UserModel model) throws Group11Exception {
        return null;
    }

    @Override
    public List<Long> insertInBatch(Collection<UserModel> models) throws Group11Exception {
        return null;
    }

    @Override
    public void deleteById(UserModel model) {

    }

    @Override
    public void deleteInBatch(Collection<UserModel> models) throws Group11Exception {

    }

    @Override
    public void updateById(UserModel model) throws Group11Exception {

    }

    @Override
    public void updateInBatch(Collection<UserModel> models) throws Group11Exception {

    }

    @Override
    public long deleteLogicallyByIds(Collection<Long> ids, String updateBy) throws Group11Exception {
        return 0;
    }

    @Override
    public long deleteHardByIds(Collection<Long> ids) throws Group11Exception {
        return 0;
    }

    @Override
    public List<Long> upsertInBatch(Collection<UserModel> models) throws Group11Exception {
        return null;
    }

    @Override
    public int deleteAllHardly() {
        return 0;
    }
}
