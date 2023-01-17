package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductRepository {

    Optional<Product> findById(@Param("id") Long id);

    List<Product> findAll();

    void create(Product product);

    void delete(@Param("id") Long id);

    List<Product> findAllByCategory(@Param("category") String category);

    List<Product> findAllByCategoryOrdered(@Param("category") String category, @Param("ordering") String ordering);

    Optional<Product> findByModelOrArticle(@Param("parameter") String parameter);

    void updateAmount(@Param("product") Product product);

    boolean isExistByArticleOrModel(@Param("article") String article, @Param("model") String model);

}
