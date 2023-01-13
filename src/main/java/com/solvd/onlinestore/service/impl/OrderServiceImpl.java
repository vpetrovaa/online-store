package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.repository.BasketRepository;
import com.solvd.onlinestore.repository.OrderRepository;
import com.solvd.onlinestore.service.OrderPointService;
import com.solvd.onlinestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final OrderPointService orderPointService;

    @Override
    public Order save(Order order, Long userId) {
        List<Basket> baskets = basketRepository.findAllByUserId(userId);
        if(baskets.isEmpty()){
            throw new ResourceDoesNotExistException("Your basket is empty");
        }
        BigDecimal amount = BigDecimal.valueOf(0.0);
        for(Basket b: baskets) {
            amount = amount.add(b.getProduct().getCost());
        }
        orderRepository.save(order, userId, amount);
        baskets.forEach((b) -> orderPointService.save(b, order));
        basketRepository.deleteAllByUserId(userId);
        return order;
    }

    @Override
    public List<Order> findAllByStatus(String status) {
        List<Order> orders = orderRepository.findAllByStatus(status);
        return orders;
    }

    @Override
    public Order updateStatus(Long id) {
        Order order = findById(id);
        order.setStatus(Order.StatusEnum.valueOf("TRUE"));
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
