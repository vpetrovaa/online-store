package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.Basket;

import java.util.List;

public interface BasketService {

    Basket save(Long productId, Long userId);

    List<Basket> findAllByUser(Long id);

    void deleteById(Long id);

}
