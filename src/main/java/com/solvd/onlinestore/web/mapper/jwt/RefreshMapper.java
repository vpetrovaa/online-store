package com.solvd.onlinestore.web.mapper.jwt;

import com.solvd.onlinestore.web.security.Refresh;
import com.solvd.onlinestore.web.dto.jwt.RefreshDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshMapper {

    Refresh dtoToEntity(RefreshDto refreshDto);

    RefreshDto entityToDto(Refresh refresh);

}
