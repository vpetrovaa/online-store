package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.repository.WarehouseRepository;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public Warehouse create(Warehouse warehouse, Long productId) {
        warehouseRepository.create(warehouse, productService.findById(productId));
        productService.updateAmount(productId, warehouse.getAmount());
        return warehouse;
    }

}
