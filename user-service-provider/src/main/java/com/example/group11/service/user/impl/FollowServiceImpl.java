package com.example.group11.service.user.impl;

import com.example.group11.commons.utils.BaseServiceImpl;
import com.example.group11.entity.sql.Follow;
import com.example.group11.entity.sql.User;
import com.example.group11.model.FollowModel;
import com.example.group11.model.UserModel;
import com.example.group11.repository.user.FollowRepository;
import com.example.group11.service.user.FollowService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileName: FollowServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:20
 */

@DubboService(version = "1.0.0", interfaceClass = com.example.group11.service.user.FollowService.class)
public class FollowServiceImpl extends BaseServiceImpl<FollowModel, Follow, Long> implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Override
    protected Class<FollowModel> getModelType() {
        return FollowModel.class;
    }

    @Override
    protected Class<Follow> getEntityType() {
        return Follow.class;
    }

    @Override
    public List<Long> queryFollowingIdByFollowedId(Long FollowedId) {
        Pageable pageable = Pageable.unpaged();
        Specification<Follow> sepc = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("beFollowedUserId"), FollowedId));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return followRepository.findAll(sepc, pageable).getContent().stream().map(Follow::getFollowingUserId)
                .collect(Collectors.toList());
    }
}
