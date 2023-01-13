package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.product.ProductSearchParameter;
import com.solvd.onlinestore.web.dto.product.ProductSearchParameterDto;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import com.solvd.onlinestore.web.mapper.product.ProductMapper;
import com.solvd.onlinestore.web.mapper.product.ProductSearchParameterMapper;
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
    private final ProductSearchParameterMapper parameterMapper;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@RequestBody @Validated ProductDto productDto){
        Product product = productMapper.dtoToEntity(productDto);
        productDto = productMapper.entityToDto(productService.save(product));
        return productDto;
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll(){
        List<Product> products = productService.findAll();
        List<ProductDto> productsDto = productMapper.entityToDto(products);
        return productsDto;
    }

    @GetMapping(value="/products/{id}")
    public ProductDto findById(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        ProductDto productDto = productMapper.entityToDto(product);
        return productDto;
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id){
        productService.delete(id);
    }

    @GetMapping(value="/users/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategory(@RequestParam("category") String category){
        List<Product> products = productService.findAllByCategory(category);
        List<ProductDto> productsDto = productMapper.entityToDto(products);
        return productsDto;
    }

    @GetMapping(value="/users/products/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategoryOrdered(@PathVariable("category") String category, @RequestParam("ordering") String ordering){
        List<Product> products = productService.findAllByCategoryOrdered(category, ordering);
        List<ProductDto> productsDto = productMapper.entityToDto(products);
        return productsDto;
    }

    @GetMapping(value="/users/products/search")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findByModelOrArticle(@RequestBody @Validated ProductSearchParameterDto parameterDto){
        ProductSearchParameter parameter = parameterMapper.dtoToEntity(parameterDto);
        Product product = productService.findByModelOrArticle(parameter);
        ProductDto productDto = productMapper.entityToDto(product);
        return productDto;
    }

}
