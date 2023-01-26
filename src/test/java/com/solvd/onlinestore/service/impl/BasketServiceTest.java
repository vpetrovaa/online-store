package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.repository.BasketRepository;
import com.solvd.onlinestore.service.BasketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    private BasketService basketService;

    @Mock
    private BasketRepository basketRepository;

    @BeforeEach
    void setUp(){
        basketService = new BasketServiceImpl(basketRepository);
    }

    @Test
    void verifyCreatePassedTest() {
        Long userId = 1L;
        Long productId = 1L;
        when(basketRepository.isExistByProductAndUser(anyLong(), anyLong())).thenReturn(false);
        basketService.create(productId, userId);
        verify(basketRepository, times(1)).isExistByProductAndUser(productId, userId);
        verify(basketRepository, times(1)).create(new Basket(), productId, userId);
    }

    @Test
    void verifyCreateFailedTest() {
        Long userId = 1L;
        Long productId = 1L;
        when(basketRepository.isExistByProductAndUser(anyLong(), anyLong())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, () -> basketService.create(productId, userId),
                "Assert ResourceAlreadyExistsException");
        verify(basketRepository, times(1)).isExistByProductAndUser(productId, userId);
    }

    @Test
    void verifyFindAllByUserTest() {
        Basket basket = generateBasket();
        List<Basket> baskets = new ArrayList<>(List.of(basket));
        Long userId = 1L;
        when(basketRepository.findAllByUserId(anyLong())).thenReturn(baskets);
        List<Basket> basketsFounded = basketService.findAllByUser(userId);
        assertEquals(baskets, basketsFounded, "Assert that basket and basketFounded are equals");
        verify(basketRepository, times(1)).findAllByUserId(userId);
    }


    @Test
    void verifyDeleteTest() {
        Long basketId = 1L;
        basketRepository.delete(basketId);
        verify(basketRepository, times(1)).delete(basketId);
    }

    @Test
    void verifyDeleteAllByUserIdTest() {
        Long userId = 1L;
        basketRepository.deleteAllByUserId(userId);
        verify(basketRepository, times(1)).deleteAllByUserId(userId);
    }

    private Product generateProduct() {
        return new Product(1L, Product.Category.SOFAS, "6574537",
                "TR34h", "New interesting excellent confident delicious blue big sofa",
                BigDecimal.valueOf(1236.56), "Green Street, 7", 10);
    }

    private User generateUser() {
        return new User(1L, "Katya", "Ivanova", "katya@mail.ru", "375296625296", User.Role.ROLE_USER,
                "helloWorld123", LocalDateTime.now());
    }

    private Basket generateBasket() {
        User user = generateUser();
        Product product = generateProduct();
        return new Basket(1L, product, user);
    }

}