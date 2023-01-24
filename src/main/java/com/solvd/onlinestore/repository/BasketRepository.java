package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Basket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BasketRepository {

    void create(Basket basket, @Param("productId") Long productId, @Param("userId") Long userId);

    void delete(Long id);

    List<Basket> findAllByUserId(Long userId);

    Optional<Basket> findById(Long id);

    boolean isExistByProductAndUser(@Param("productId") Long productId, @Param("userId") Long userId);

    void deleteAllByUserId(Long userId);

}
