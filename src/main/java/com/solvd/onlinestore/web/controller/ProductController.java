package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Warehouse;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.service.WarehouseService;
import com.solvd.onlinestore.web.dto.WarehouseDto;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import com.solvd.onlinestore.web.mapper.WarehouseMapper;
import com.solvd.onlinestore.web.mapper.product.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Product", description = "Controller for admin authority to manipulate products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @Operation(summary = "Create new warehouse",
            description = "Create new warehouse to add amount to product")
    @PostMapping("/products/{productId}/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseDto create(@Parameter(description = "Product id") @PathVariable("productId") Long productId,
                               @Parameter(description = "Warehouse with given amount") @RequestBody @Validated WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.dtoToEntity(warehouseDto);
        warehouse = warehouseService.create(warehouse, productId);
        warehouseDto = warehouseMapper.entityToDto(warehouse);
        return warehouseDto;
    }

    @Operation(summary = "Create new product",
            description = "Add new product to store catalog")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Parameter(description = "Product to be added") @RequestBody @Validated ProductDto productDto) {
        Product product = productMapper.dtoToEntity(productDto);
        productDto = productMapper.entityToDto(productService.create(product));
        return productDto;
    }

    @Operation(summary = "Find all products",
            description = "Find all products from the catalog")
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll() {
        List<Product> products = productService.findAll();
        return productMapper.entityToDto(products);
    }

    @Operation(summary = "Find product by id",
            description = "Find one product in catalog by id")
    @GetMapping(value = "/products/{id}")
    public ProductDto findById(@Parameter(description = "Product id") @PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return productMapper.entityToDto(product);
    }

    @Operation(summary = "Delete product by id",
            description = "Delete product from a catalog by id")
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Product id") @PathVariable(name = "id") Long id) {
        productService.delete(id);
    }

}
