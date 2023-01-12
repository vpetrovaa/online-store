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
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductSearchParameterMapper parameterMapper;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@RequestBody @Validated ProductDto productDto){
        Product product = productMapper.productDtoToProduct(productDto);
        productDto = productMapper.productToProductDto(productService.save(product));
        return productDto;
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll(){
        List<Product> products = productService.findAll();
        return productMapper.productsToProductDto(products);
    }

    @GetMapping(value="/products/{id}")
    public ProductDto findById(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        return productMapper.productToProductDto(product);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
    }

    @GetMapping(value="/users/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategory(@RequestParam("category") String category){
        List<Product> products = productService.findAllByCategory(category);
        return productMapper.productsToProductDto(products);
    }

    @GetMapping(value="/users/products/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategoryOrdered(@PathVariable("category") String category, @RequestParam("ordering") String ordering){
        List<Product> products = productService.findAllByCategoryOrdered(category, ordering);
        return productMapper.productsToProductDto(products);
    }

    @GetMapping(value="/users/products/search")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findByModelOrArticle(@RequestBody @Validated ProductSearchParameterDto parameterDto){
        ProductSearchParameter parameter = parameterMapper.parameterDtoToParameter(parameterDto);
        Product product = productService.findByModelOrArticle(parameter);
        return productMapper.productToProductDto(product);
    }

}
