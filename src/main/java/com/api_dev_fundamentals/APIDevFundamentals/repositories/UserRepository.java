package com.api_dev_fundamentals.APIDevFundamentals.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.api_dev_fundamentals.APIDevFundamentals.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();

    User findByName(String name);
}
