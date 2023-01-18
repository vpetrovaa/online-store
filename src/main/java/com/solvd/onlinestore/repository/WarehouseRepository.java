package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.domain.product.Product;

public interface WarehouseRepository {

    void create(Warehouse warehouse, Product product);

}
