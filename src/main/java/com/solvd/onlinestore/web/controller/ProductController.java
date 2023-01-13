package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.service.WarehouseService;
import com.solvd.onlinestore.web.dto.WarehouseDto;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import com.solvd.onlinestore.web.mapper.WarehouseMapper;
import com.solvd.onlinestore.web.mapper.product.ProductMapper;
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
    private final ProductMapper productMapper;
    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @PostMapping("/products/{productId}/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDto create(@PathVariable("productId") Long productId, @RequestBody @Validated WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.dtoToEntity(warehouseDto);
        warehouse = warehouseService.create(warehouse, productId);
        warehouseDto = warehouseMapper.entityToDto(warehouse);
        return warehouseDto;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestBody @Validated ProductDto productDto) {
        Product product = productMapper.dtoToEntity(productDto);
        productDto = productMapper.entityToDto(productService.create(product));
        return productDto;
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll() {
        List<Product> products = productService.findAll();
        return productMapper.entityToDto(products);
    }

    @GetMapping(value = "/products/{id}")
    public ProductDto findById(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return productMapper.entityToDto(product);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        productService.delete(id);
    }

    @GetMapping(value = "/users/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategory(@RequestParam("category") String category) {
        List<Product> products = productService.findAllByCategory(category);
        return productMapper.entityToDto(products);
    }

}
