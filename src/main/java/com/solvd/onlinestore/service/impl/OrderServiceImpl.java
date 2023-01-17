package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.repository.OrderRepository;
import com.solvd.onlinestore.service.BasketService;
import com.solvd.onlinestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BasketService basketService;

    @Override
    public Order create(Order order, Long userId) {
        List<Basket> baskets = basketService.findAllByUser(userId);
        if (baskets.isEmpty()) {
            throw new ResourceDoesNotExistException("Your basket is empty");
        }
        BigDecimal amount = baskets.stream()
                .map(b -> b.getProduct().getCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderRepository.create(order, userId, amount);
        basketService.deleteAllByUserId(userId);
        return order;
    }

    @Override
    public List<Order> findAllByStatus(String status) {
        return orderRepository.findAllByStatus(status);
    }

    @Override
    public Order updateStatus(Long id) {
        Order order = findById(id);
        orderRepository.updateStatus(order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("There are no order with id" + id));
        return order;
    }

}
