package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.service.OrderService;
import com.solvd.onlinestore.web.dto.OrderDto;
import com.solvd.onlinestore.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllByStatus(@RequestParam("status") String status) {
        List<Order> orders = orderService.findAllByStatus(status);
        return orderMapper.entityToDto(orders);
    }

    @PutMapping(value = "/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto confirm(@PathVariable("id") Long id) {
        Order order = orderService.updateStatus(id);
        return orderMapper.entityToDto(order);
    }

}
