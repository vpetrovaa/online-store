package com.solvd.onlinestore.web.mapper;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.web.dto.WarehouseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseDto entityToDto(Warehouse warehouse);

    Warehouse dtoToEntity(WarehouseDto warehouseDto);

}
