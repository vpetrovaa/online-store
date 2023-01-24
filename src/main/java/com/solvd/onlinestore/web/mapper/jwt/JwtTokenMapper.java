package com.solvd.onlinestore.web.mapper.jwt;

import com.solvd.onlinestore.domain.jwt.JwtToken;
import com.solvd.onlinestore.web.dto.jwt.JwtTokenDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtTokenMapper {

    JwtToken dtoToEntity(JwtTokenDto responseUserDto);

    JwtTokenDto entityToDto(JwtToken responseUser);

}
