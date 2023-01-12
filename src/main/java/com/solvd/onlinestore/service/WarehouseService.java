package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.Warehouse;

public interface WarehouseService {

    Warehouse save(Warehouse warehouse, Long productId);

}
