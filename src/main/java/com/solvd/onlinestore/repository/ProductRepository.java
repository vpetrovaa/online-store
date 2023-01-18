package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductRepository {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void create(Product product);

    void delete(Long id);

    List<Product> findAllByCategory(String category);

    List<Product> findAllByCategoryOrdered(@Param("category") String category, @Param("ordering") String ordering);

    Optional<Product> findByModelOrArticle(String parameter);

    void updateAmount(Product product);

    boolean isExistByArticleOrModel(@Param("article") String article, @Param("model") String model);

}
