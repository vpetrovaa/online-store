package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;

public interface OrderPointService {

    void save(Basket basket, Order order);

}
