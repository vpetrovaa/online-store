package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(Long id);

    boolean isExistsByEmail(String email);

}
