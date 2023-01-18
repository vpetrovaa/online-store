package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.repository.OrderPointRepository;
import com.solvd.onlinestore.service.OrderPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderPointServiceImpl implements OrderPointService {

    private final OrderPointRepository orderPointRepository;

    @Override
    @Transactional
    public void create(Basket basket, Order order) {
        orderPointRepository.create(basket, order);
    }

}
