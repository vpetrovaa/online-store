package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.User;

public interface UserService {

    User findById(Long id);

    User save(User user);

}
