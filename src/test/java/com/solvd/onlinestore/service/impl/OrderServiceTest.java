package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.repository.OrderRepository;
import com.solvd.onlinestore.service.BasketService;
import com.solvd.onlinestore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BasketService basketService;

    @BeforeEach
    void setUp(){
        orderService = new OrderServiceImpl(orderRepository, basketService);
    }

    @Test
    void verifyCreatePassedTest() {
        Basket basket = generateBasket();
        List<Basket> baskets = new ArrayList<>(List.of(basket));
        Order order = generateOrder();
        Long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(1236.56);
        when(basketService.findAllByUser(anyLong())).thenReturn(baskets);
        Order orderCreated = orderService.create(order, userId);
        assertEquals(order, orderCreated, "Assert that order and orderCreated are equals");
        verify(basketService, times(1)).findAllByUser(userId);
        verify(orderRepository, times(1)).create(order, userId, amount);
        verify(basketService, times(1)).deleteAllByUserId(userId);
    }

    @Test
    void verifyCreateFailedTest() {
        Order order = generateOrder();
        Long userId = 1L;
        when(basketService.findAllByUser(anyLong())).thenReturn(new ArrayList<>());
        assertThrows(ResourceDoesNotExistException.class, () -> orderService.create(order, userId), "Assert ResourceDoesNotExistException");
        verify(basketService, times(1)).findAllByUser(userId);
    }

    @Test
    void verifyFindAllByStatusTest() {
        Order order = generateOrder();
        List<Order> orders = new ArrayList<>(List.of(order));
        String status = "FALSE";
        when(orderRepository.findAllByStatus(anyString())).thenReturn(orders);
        List<Order> ordersFounded = orderService.findAllByStatus(status);
        assertEquals(orders, ordersFounded,"Assert that order and orderFounded are equals");
        verify(orderRepository, times(1)).findAllByStatus(status);
    }

    @Test
    void verifyFindByIdPassedTest() {
        Order order = generateOrder();
        Long orderId = 1L;
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Order orderFounded = orderService.findById(orderId);
        assertEquals(order, orderFounded, "Assert that order and orderFounded are equals");
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void verifyFindByIdFailedTest() {
        Long orderId = 1L;
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> orderService.findById(orderId), "Assert ResourceDoesNotExistException");
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void verifyUpdateStatusTest() {
        Order order = generateOrder();
        Long orderId = 1L;
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        orderService.updateStatus(orderId);
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).updateStatus(order);
    }

    private User generateUser() {
        return new User(1L, "Katya", "Ivanova", "katya@mail.ru", "375296625296", User.Role.ROLE_USER,
                "helloWorld123", LocalDateTime.now());
    }

    private Order generateOrder() {
        User user = generateUser();
        return new Order(1L, user, BigDecimal.valueOf(1236.56), Order.Delivery.COURIER,
                Order.Payment.CARD, LocalDateTime.now(), Order.Status.FALSE, "Black Street, 4", LocalDate.now());
    }

    private Product generateProduct() {
        return new Product(1L, Product.Category.SOFAS, "6574537",
                "TR34h", "New interesting excellent confident delicious blue big sofa",
                BigDecimal.valueOf(1236.56), "Green Street, 7", 10);
    }

    private Basket generateBasket() {
        User user = generateUser();
        Product product = generateProduct();
        return new Basket(1L, product, user);
    }

}