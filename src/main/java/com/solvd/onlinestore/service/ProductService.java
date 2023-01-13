package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.product.ProductSearchParameter;

import java.util.List;

public interface ProductService {

    Product findById(Long id);

    List<Product> findAll();

    Product save(Product product);

    void delete(Long id);

    List<Product> findAllByCategory(String category);

    List<Product> findAllByCategoryOrdered(String category, String sorting);

    Product findByModelOrArticle(ProductSearchParameter parameter);

    void updateAmount(Long id, int amount);

}
