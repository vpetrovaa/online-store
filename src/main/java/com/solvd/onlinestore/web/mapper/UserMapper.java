package com.solvd.onlinestore.web.mapper;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, BasketMapper.class})
public interface UserMapper {

    UserDto entityToDto(User user);

    User dtoToEntity(UserDto userDto);

}
