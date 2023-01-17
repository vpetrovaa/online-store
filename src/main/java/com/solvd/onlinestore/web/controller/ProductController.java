package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final WarehouseService warehouseService;

    @PostMapping("/products/{productId}/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    public Warehouse create(@PathVariable("productId") Long productId, @RequestBody @Validated Warehouse warehouse) {
        return warehouseService.create(warehouse, productId);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Validated Product product) {
        return productService.create(product);
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/products/{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        productService.delete(id);
    }

}
