package com.test_stm.repository;

import org.springframework.data.repository.CrudRepository;

import com.test_stm.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    void update(User user);
}
