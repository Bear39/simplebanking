package com.simplebanking.service;

import com.simplebanking.domain.User;
import com.simplebanking.domain.security.UserRole;

import java.util.Set;

public interface UserService {
    User findByUsername(String username);

    User findByEmail(String email);

//    User findByAccount_ID(int account_id);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void save (User user);

    User saveUser (User user);

    User createUser (User user, Set<UserRole> userRoles);

}
