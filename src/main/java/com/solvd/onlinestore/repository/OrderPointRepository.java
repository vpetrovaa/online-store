package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;

public interface OrderPointRepository {

    void create(Basket basket, Order order);

}
