package com.solvd.onlinestore.web.mapper.jwt;

import com.solvd.onlinestore.domain.jwt.RequestUser;
import com.solvd.onlinestore.web.dto.jwt.RequestUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestUserMapper {

    RequestUserDto entityToDto(RequestUser requestUser);

    RequestUser dtoToEntity(RequestUserDto requestUserDto);

}
