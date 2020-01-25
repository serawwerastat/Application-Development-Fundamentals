package com.fundamentals.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.fundamentals.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();

    User findByName(String name);
}
