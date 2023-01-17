package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.product.ProductSearchParameter;
import com.solvd.onlinestore.service.BasketService;
import com.solvd.onlinestore.service.OrderService;
import com.solvd.onlinestore.service.ProductService;
import com.solvd.onlinestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final BasketService basketService;
    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping("/users/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Validated User user) {
        return userService.create(user);
    }

    @PostMapping("/users/{userId}/baskets/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Basket create(@PathVariable("productId") Long productId, @PathVariable("userId") Long userId) {
        return basketService.create(productId, userId);
    }

    @DeleteMapping("/users/baskets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id) {
        basketService.delete(id);
    }

    @GetMapping("/users/{id}/baskets")
    @ResponseStatus(HttpStatus.OK)
    public List<Basket> findAllByUser(@PathVariable(name = "id") Long id) {
        return basketService.findAllByUser(id);
    }

    @PostMapping(value = "/users/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@PathVariable("userId") Long userId, @RequestBody @Validated Order order) {
        return orderService.create(order, userId);
    }

    @GetMapping(value = "/users/products/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> findByCategoryOrdered(@PathVariable("category") String category, @RequestParam("ordering") String ordering) {
        List<Product> products = productService.findAllByCategoryOrdered(category, ordering);
        return productService.findAllByCategoryOrdered(category, ordering);
    }

    @GetMapping(value = "/users/products/search")
    @ResponseStatus(HttpStatus.OK)
    public Product findByModelOrArticle(@RequestBody @Validated ProductSearchParameter parameter) {
        return productService.findByModelOrArticle(parameter);
    }

    @GetMapping(value = "/users/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> findByCategory(@RequestParam("category") String category) {
        return productService.findAllByCategory(category);
    }


}
