package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void create(Order order, Long userId, BigDecimal amount);

    List<Order> findAllByStatus(String status);

    void updateStatus(Order order);

    Optional<Order> findById(Long id);

}
