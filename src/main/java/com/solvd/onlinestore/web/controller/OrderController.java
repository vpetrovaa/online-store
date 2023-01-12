package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.service.OrderService;
import com.solvd.onlinestore.web.dto.OrderDto;
import com.solvd.onlinestore.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping(value = "/users/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto save(@PathVariable("userId") Long userId, @RequestBody @Validated OrderDto orderDto){
        Order order = orderMapper.orderDtoToOrder(orderDto);
        order = orderService.save(order, userId);
        orderDto = orderMapper.orderToOrderDto(order);
        return orderDto;
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllByStatus(@RequestParam("status") String status){
        List<Order> orders = orderService.findAllByStatus(status);
        return orderMapper.ordersToOrderDto(orders);
    }

    @PutMapping(value = "/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto confirm(@PathVariable("id") Long id){
        Order order = orderService.updateStatus(id);
        OrderDto orderDto = orderMapper.orderToOrderDto(order);
        return  orderDto;
    }

}
