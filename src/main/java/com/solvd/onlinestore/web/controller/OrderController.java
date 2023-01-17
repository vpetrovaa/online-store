package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAllByStatus(@RequestParam("status") String status) {
        return orderService.findAllByStatus(status);
    }

    @PutMapping(value = "/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order confirm(@PathVariable("id") Long id) {
        return orderService.updateStatus(id);
    }

}
