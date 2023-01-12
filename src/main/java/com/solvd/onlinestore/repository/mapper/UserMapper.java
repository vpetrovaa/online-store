package com.solvd.onlinestore.repository.mapper;

import com.solvd.onlinestore.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class UserMapper {

    @SneakyThrows
    public static User mapByIdNameSurname(ResultSet rs){
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        user.setEmail(rs.getString("user_email"));
        return user;
    }

}
