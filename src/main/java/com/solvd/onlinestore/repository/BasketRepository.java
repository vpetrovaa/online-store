package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketRepository {

    void create(Basket basket, Long productId, Long userId);

    void delete(Long id);

    List<Basket> findAllByUserId(Long id);

    Optional<Basket> findById(Long id);

    boolean isExistByProductAndUser(Long productId, Long userId);

    void deleteAllByUserId(Long id);

}
