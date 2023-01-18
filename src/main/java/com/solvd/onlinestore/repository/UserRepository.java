package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.User;

import java.util.Optional;

public interface UserRepository {

    void create(User user);

    Optional<User> findById(Long id);

    boolean isExistByEmail(String email);

}
