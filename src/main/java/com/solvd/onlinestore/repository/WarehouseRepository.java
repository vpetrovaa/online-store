package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WarehouseRepository {

    void create(@Param("warehouse") Warehouse warehouse,@Param("product") Product product);

}
