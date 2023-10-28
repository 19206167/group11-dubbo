package com.example.group11.repository.impl.user;

import com.example.group11.commons.utils.CheckUtil;
import com.example.group11.entity.sql.User;
import com.example.group11.enums.PageEnum;
import com.example.group11.repository.custom.UserRepositoryCustom;
import com.example.group11.vo.query.UserQueryVO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    public List<User> queryFanListByUserId(Long userId, UserQueryVO params) {

        Integer pageNo = CheckUtil.isNotEmpty(params.getPageNo()) ? params.getPageNo() : PageEnum.DEFAULT_PAGE_NO.getKey();
        Integer pageSize = CheckUtil.isNotEmpty(params.getPageSize()) ? params.getPageSize() : PageEnum.DEFAULT_PAGE_SIZE.getKey();

        String jpql = "select u from User u where u.id in (select f.followingUserId from Follow f where f.beFollowedUserId = :p1 and f.deleted = false) and u.deleted = false";

        if (CheckUtil.isNotEmpty(params.getId())) {
            jpql += " and u.id = :p2";
        }
        if (CheckUtil.isNotEmpty(params.getLoginName())) {
            jpql += " and u.loginName like :p3";
        }
        if (CheckUtil.isNotEmpty(params.getUserName())) {
            jpql += " and u.userName like :p4";
        }
        if (CheckUtil.isNotEmpty(params.getEmail())) {
            jpql += " and u.email like :p5";
        }
        if (CheckUtil.isNotEmpty(params.getPhone())) {
            jpql += " and u.phone like :p6";
        }
        Query query = entityManager.createQuery(jpql);
        query.setParameter("p1", userId);
        if (CheckUtil.isNotEmpty(params.getId())) {
            query.setParameter("p2", params.getId());
        }
        if (CheckUtil.isNotEmpty(params.getLoginName())) {
            query.setParameter("p3", "%" + params.getLoginName() + "%");
        }
        if (CheckUtil.isNotEmpty(params.getUserName())) {
            query.setParameter("p4", "%" + params.getUserName() + "%");
        }
        if (CheckUtil.isNotEmpty(params.getEmail())) {
            query.setParameter("p5", "%" + params.getEmail() + "%");
        }
        if (CheckUtil.isNotEmpty(params.getPhone())) {
            query.setParameter("p6", "%" + params.getPhone() + "%");
        }
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);
        List<User> userList = query.getResultList();
        return userList;
    }


    public List<User> queryFollowListByUserId(Long userId, UserQueryVO params) {

        Integer pageNo = CheckUtil.isNotEmpty(params.getPageNo()) ? params.getPageNo() : PageEnum.DEFAULT_PAGE_NO.getKey();
        Integer pageSize = CheckUtil.isNotEmpty(params.getPageSize()) ? params.getPageSize() : PageEnum.DEFAULT_PAGE_SIZE.getKey();

        String jpql = "select u from User u where u.id in (select f.beFollowedUserId from Follow f where f.followingUserId = :p1 and f.deleted = false) and u.deleted = false";

        if (CheckUtil.isNotEmpty(params.getId())) {
            jpql += " and u.id = :p2";
        }
        if (CheckUtil.isNotEmpty(params.getLoginName())) {
            jpql += " and u.loginName like :p3";
        }
        if (CheckUtil.isNotEmpty(params.getUserName())) {
            jpql += " and u.userName like :p4";
        }
        if (CheckUtil.isNotEmpty(params.getEmail())) {
            jpql += " and u.email like :p5";
        }
        if (CheckUtil.isNotEmpty(params.getPhone())) {
            jpql += " and u.phone like :p6";
        }
        Query query = entityManager.createQuery(jpql);
        query.setParameter("p1", userId);
        if (CheckUtil.isNotEmpty(params.getId())) {
            query.setParameter("p2", params.getId());
        }
        if (CheckUtil.isNotEmpty(params.getLoginName())) {
            query.setParameter("p3", "%" + params.getLoginName() + "%");
        }
        if (CheckUtil.isNotEmpty(params.getUserName())) {
            query.setParameter("p4", "%" + params.getUserName() + "%");
        }
        if (CheckUtil.isNotEmpty(params.getEmail())) {
            query.setParameter("p5", "%" + params.getEmail() + "%");
        }
        if (CheckUtil.isNotEmpty(params.getPhone())) {
            query.setParameter("p6", "%" + params.getPhone() + "%");
        }
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);
        List<User> userList = query.getResultList();
        return userList;
    }
}


