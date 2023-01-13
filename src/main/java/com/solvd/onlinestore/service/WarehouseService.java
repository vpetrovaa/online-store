package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.Warehouse;

public interface WarehouseService {

    Warehouse create(Warehouse warehouse, Long productId);

}
