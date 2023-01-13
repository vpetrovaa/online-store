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
import com.solvd.onlinestore.web.dto.BasketDto;
import com.solvd.onlinestore.web.dto.OrderDto;
import com.solvd.onlinestore.web.dto.UserDto;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import com.solvd.onlinestore.web.dto.product.ProductSearchParameterDto;
import com.solvd.onlinestore.web.mapper.BasketMapper;
import com.solvd.onlinestore.web.mapper.OrderMapper;
import com.solvd.onlinestore.web.mapper.UserMapper;
import com.solvd.onlinestore.web.mapper.product.ProductMapper;
import com.solvd.onlinestore.web.mapper.product.ProductSearchParameterMapper;
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
    private final UserMapper userMapper;
    private final BasketService basketService;
    private final BasketMapper basketMapper;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductSearchParameterMapper parameterMapper;

    @PostMapping("/users/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Validated UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        user = userService.create(user);
        userDto = userMapper.entityToDto(user);
        return userDto;
    }

    @PostMapping("/users/{userId}/baskets/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BasketDto create(@PathVariable("productId") Long productId, @PathVariable("userId") Long userId) {
        return basketMapper.entityToDto(basketService.create(productId, userId));
    }

    @DeleteMapping("/users/baskets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id) {
        basketService.delete(id);
    }

    @GetMapping("/users/{id}/baskets")
    @ResponseStatus(HttpStatus.OK)
    public List<BasketDto> findAllByUser(@PathVariable(name = "id") Long id) {
        List<Basket> baskets = basketService.findAllByUser(id);
        return basketMapper.entityToDto(baskets);
    }

    @PostMapping(value = "/users/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@PathVariable("userId") Long userId, @RequestBody @Validated OrderDto orderDto) {
        Order order = orderMapper.dtoToEntity(orderDto);
        order = orderService.create(order, userId);
        orderDto = orderMapper.entityToDto(order);
        return orderDto;
    }

    @GetMapping(value = "/users/products/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategoryOrdered(@PathVariable("category") String category, @RequestParam("ordering") String ordering) {
        List<Product> products = productService.findAllByCategoryOrdered(category, ordering);
        return productMapper.entityToDto(products);
    }

    @GetMapping(value = "/users/products/search")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findByModelOrArticle(@RequestBody @Validated ProductSearchParameterDto parameterDto) {
        ProductSearchParameter parameter = parameterMapper.dtoToEntity(parameterDto);
        Product product = productService.findByModelOrArticle(parameter);
        return productMapper.entityToDto(product);
    }


}
