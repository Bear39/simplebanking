package com.simplebanking.dao;

import com.simplebanking.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
//    User findByAccount_ID(int account_id);
}
