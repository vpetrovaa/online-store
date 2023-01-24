package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.service.OrderService;
import com.solvd.onlinestore.web.dto.OrderDto;
import com.solvd.onlinestore.web.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Order", description = "Controller for admin authority to manipulate confirmed/not confirmed orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @Operation(summary = "Find all orders by status",
            description = "Find all orders by status : TRUE - confirmed, FALSE - not confirmed")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllByStatus(@Parameter(description = "String status - TRUE/FALSE") @RequestParam("status") String status) {
        List<Order> orders = orderService.findAllByStatus(status);
        return orderMapper.entityToDto(orders);
    }

    @Operation(summary = "Confirm the order",
            description = "Set TRUE status for order and make it confirmed")
    @PutMapping(value = "/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermissionToConfirmOrder(#id)")
    public OrderDto confirm(@Parameter(description = "Order id") @PathVariable("id") Long id) {
        Order order = orderService.updateStatus(id);
        return orderMapper.entityToDto(order);
    }

}
