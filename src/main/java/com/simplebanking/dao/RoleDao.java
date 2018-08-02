package com.simplebanking.dao;

import org.springframework.data.repository.CrudRepository;

import com.simplebanking.domain.security.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
