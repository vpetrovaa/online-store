package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {

    void create(User user);

    Optional<User> findById(@Param("id") Long id);

    boolean isExistByEmail(@Param("email") String email);

}
