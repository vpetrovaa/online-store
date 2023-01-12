package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order, Long userId);

    List<Order> findAllByStatus(String status);

    Order updateStatus(Long id);

    Order findById(Long id);

}
