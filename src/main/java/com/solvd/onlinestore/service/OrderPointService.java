package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;

public interface OrderPointService {

    void create(Basket basket, Order order);

}
