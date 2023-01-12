package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void save(Product product);

    void deleteById(Long id);

    List<Product> findAllByCategory(String category);

    List<Product> findAllByCategoryOrdered(String category, String ordering);

    Optional<Product> findByModelOrArticle(String parameter);

    void updateAmount(Product product);

    boolean isExistByArticleOrModel(String article, String model);

}
