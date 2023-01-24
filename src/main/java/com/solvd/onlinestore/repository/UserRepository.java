package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {

    void create(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean isExistByEmail(String email);

}
