package com.example.group11.repository.user;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.User;
import com.example.group11.repository.custom.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

/**
 * FileName: UserRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 21:25
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long>, UserRepositoryCustom {

    User findByLoginNameAndDeleted(String loginName, Boolean deleted);
}
