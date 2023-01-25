package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.repository.WarehouseRepository;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.service.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    private WarehouseService warehouseService;

    @Mock
    private ProductService productService;

    @Mock
    private WarehouseRepository warehouseRepository;


    @BeforeEach
    void setUp(){
        warehouseService = new WarehouseServiceImpl(warehouseRepository, productService);
    }

    @Test
    void verifyCreatePassedTest() {
        Product product = generateProduct();
        Warehouse warehouse = generateWarehouse();
        Long productId = 1L;
        int amount = 100;
        when(productService.findById(anyLong())).thenReturn(product);
        Warehouse warehouseCreated = warehouseService.create(warehouse, productId);
        assertEquals(warehouse, warehouseCreated, "Assert that warehouse and warehouseCreated are equals");
        verify(warehouseRepository, times(1)).create(warehouse, product);
        verify(productService, times(1)).updateAmount(productId, amount);
        verify(productService, times(1)).findById(productId);
    }

    private Product generateProduct() {
        return new Product(1L, Product.Category.SOFAS, "6574537",
                "TR34h", "New interesting excellent confident delicious blue big sofa",
                BigDecimal.valueOf(1356.99), "Green Street, 7", 10);
    }

    private Warehouse generateWarehouse() {
        Product product = generateProduct();
        return new Warehouse(1L, 100, product);
    }

}