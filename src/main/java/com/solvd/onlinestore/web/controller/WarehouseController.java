package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.service.WarehouseService;
import com.solvd.onlinestore.web.dto.WarehouseDto;
import com.solvd.onlinestore.web.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @PostMapping("/products/{productId}/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDto save(@PathVariable("productId") Long productId, @RequestBody @Validated WarehouseDto warehouseDto){
        Warehouse warehouse = warehouseMapper.warehouseDtoToWarehouse(warehouseDto);
        warehouse = warehouseService.save(warehouse, productId);
        warehouseDto = warehouseMapper.warehouseToWarehouseDto(warehouse);
        return warehouseDto;
    }

}
