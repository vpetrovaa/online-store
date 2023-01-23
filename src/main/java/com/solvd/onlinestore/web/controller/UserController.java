package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.jwt.JwtToken;
import com.solvd.onlinestore.domain.jwt.Refresh;
import com.solvd.onlinestore.domain.jwt.RequestUser;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.product.ProductSearchParameter;
import com.solvd.onlinestore.service.*;
import com.solvd.onlinestore.web.dto.BasketDto;
import com.solvd.onlinestore.web.dto.OrderDto;
import com.solvd.onlinestore.web.dto.UserDto;
import com.solvd.onlinestore.web.dto.jwt.JwtTokenDto;
import com.solvd.onlinestore.web.dto.jwt.RefreshDto;
import com.solvd.onlinestore.web.dto.jwt.RequestUserDto;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import com.solvd.onlinestore.web.dto.product.ProductSearchParameterDto;
import com.solvd.onlinestore.web.mapper.BasketMapper;
import com.solvd.onlinestore.web.mapper.OrderMapper;
import com.solvd.onlinestore.web.mapper.UserMapper;
import com.solvd.onlinestore.web.mapper.jwt.JwtTokenMapper;
import com.solvd.onlinestore.web.mapper.jwt.RefreshMapper;
import com.solvd.onlinestore.web.mapper.jwt.RequestUserMapper;
import com.solvd.onlinestore.web.mapper.product.ProductMapper;
import com.solvd.onlinestore.web.mapper.product.ProductSearchParameterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
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
    private final AuthenticationService authenticationService;
    private final RequestUserMapper requestUserMapper;
    private final JwtTokenMapper jwtTokenMapper;
    private final RefreshMapper refreshMapper;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtTokenDto login(@RequestBody @Validated RequestUserDto requestUserDto) {
        RequestUser requestUser = requestUserMapper.dtoToEntity(requestUserDto);
        JwtToken jwtToken = authenticationService.login(requestUser);
        return jwtTokenMapper.entityToDto(jwtToken);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtTokenDto refresh(@RequestBody @Validated RefreshDto refreshDto) {
        Refresh refresh = refreshMapper.dtoToEntity(refreshDto);
        JwtToken jwtToken = authenticationService.refreshToken(refresh);
        return jwtTokenMapper.entityToDto(jwtToken);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Validated UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        user = userService.create(user);
        userDto = userMapper.entityToDto(user);
        return userDto;
    }

    @PostMapping("/{userId}/baskets/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermissionToAddBasket(#id)")
    public BasketDto create(@PathVariable("productId") Long productId, @PathVariable("userId") Long userId) {
        return basketMapper.entityToDto(basketService.create(productId, userId));
    }

    @DeleteMapping("/baskets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id) {
        basketService.delete(id);
    }

    @GetMapping("/{id}/baskets")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermissionToFindAllBaskets(#id)")
    public List<BasketDto> findAllByUser(@PathVariable(name = "id") Long id) {
        List<Basket> baskets = basketService.findAllByUser(id);
        return basketMapper.entityToDto(baskets);
    }

    @PostMapping(value = "/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@PathVariable("userId") Long userId, @RequestBody @Validated OrderDto orderDto) {
        Order order = orderMapper.dtoToEntity(orderDto);
        order = orderService.create(order, userId);
        orderDto = orderMapper.entityToDto(order);
        return orderDto;
    }

    @GetMapping(value = "/products/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategoryOrdered(@PathVariable("category") String category, @RequestParam("ordering") String ordering) {
        List<Product> products = productService.findAllByCategoryOrdered(category, ordering);
        return productMapper.entityToDto(products);
    }

    @GetMapping(value = "/products/search")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findByModelOrArticle(@RequestBody @Validated ProductSearchParameterDto parameterDto) {
        ProductSearchParameter parameter = parameterMapper.dtoToEntity(parameterDto);
        Product product = productService.findByModelOrArticle(parameter);
        return productMapper.entityToDto(product);
    }

    @GetMapping(value = "/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategory(@RequestParam("category") String category) {
        List<Product> products = productService.findAllByCategory(category);
        return productMapper.entityToDto(products);
    }


}
