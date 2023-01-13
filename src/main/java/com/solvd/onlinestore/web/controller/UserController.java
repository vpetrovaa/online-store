package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.service.UserService;
import com.solvd.onlinestore.web.dto.UserDto;
import com.solvd.onlinestore.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/users/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody @Validated UserDto userDto){
        User user = userMapper.dtoToEntity(userDto);
        user = userService.save(user);
        userDto = userMapper.entityToDto(user);
        return userDto;
    }

}
