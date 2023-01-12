package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;

public interface OrderPointRepository {

    void save(Basket basket, Order order);

}
