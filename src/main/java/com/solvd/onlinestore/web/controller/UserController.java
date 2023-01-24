package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.web.security.domain.JwtToken;
import com.solvd.onlinestore.web.security.domain.Refresh;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Controller for user authority to create orders and find products")
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

    @Operation(summary = "Authorization",
            description = "User's authorization by username and password")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtTokenDto login(@Parameter(description = "Request with login and password of user") @RequestBody @Validated RequestUserDto requestUserDto) {
        RequestUser requestUser = requestUserMapper.dtoToEntity(requestUserDto);
        JwtToken jwtToken = authenticationService.login(requestUser);
        return jwtTokenMapper.entityToDto(jwtToken);
    }

    @Operation(summary = "Refresh token",
            description = "Refresh access and refresh token")
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtTokenDto refresh(@Parameter(description = "Token to be regenerated") @RequestBody @Validated RefreshDto refreshDto) {
        Refresh refresh = refreshMapper.dtoToEntity(refreshDto);
        JwtToken jwtToken = authenticationService.refreshToken(refresh);
        return jwtTokenMapper.entityToDto(jwtToken);
    }

    @Operation(summary = "Registration",
            description = "User account registration")
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Parameter(description = "User to be created") @RequestBody @Validated UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        user = userService.create(user);
        userDto = userMapper.entityToDto(user);
        return userDto;
    }

    @Operation(summary = "Create basket",
            description = "Add product to basket")
    @PostMapping("/{userId}/baskets/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermissionToAddBasket(#id)")
    public BasketDto create(@Parameter(description = "Product to be added") @PathVariable("productId") Long productId,
                            @Parameter(description = "Owner's id of basket") @PathVariable("userId") Long userId) {
        return basketMapper.entityToDto(basketService.create(productId, userId));
    }

    @Operation(summary = "Delete basket by id",
            description = "Delete product from users basket")
    @DeleteMapping("/baskets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Basket id") @PathVariable(name = "id") Long id) {
        basketService.delete(id);
    }

    @Operation(summary = "Find all baskets by user",
            description = "Show all positions in user's basket")
    @GetMapping("/{id}/baskets")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermissionToFindAllBaskets(#id)")
    public List<BasketDto> findAllByUser(@Parameter(description = "User id") @PathVariable(name = "id") Long id) {
        List<Basket> baskets = basketService.findAllByUser(id);
        return basketMapper.entityToDto(baskets);
    }

    @Operation(summary = "Create order",
            description = "Generate order from all user's baskets")
    @PostMapping(value = "/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@Parameter(description = "User") @PathVariable("userId") Long userId,
                           @Parameter(description = "Order to be created") @RequestBody @Validated OrderDto orderDto) {
        Order order = orderMapper.dtoToEntity(orderDto);
        order = orderService.create(order, userId);
        orderDto = orderMapper.entityToDto(order);
        return orderDto;
    }

    @Operation(summary = "Find all by category and ordering",
            description = "Find all products by category and cost ordering")
    @GetMapping(value = "/products/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategoryOrdered(@Parameter(description = "Product category") @PathVariable("category") String category,
                                                  @Parameter(description = "Ordering - asc/desc") @RequestParam("ordering") String ordering) {
        List<Product> products = productService.findAllByCategoryOrdered(category, ordering);
        return productMapper.entityToDto(products);
    }

    @Operation(summary = "Find by model or article",
            description = "Find product by unique field - model or article")
    @GetMapping(value = "/products/search")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findByModelOrArticle(@Parameter(description = "Criteria for searching") @RequestBody @Validated ProductSearchParameterDto parameterDto) {
        ProductSearchParameter parameter = parameterMapper.dtoToEntity(parameterDto);
        Product product = productService.findByModelOrArticle(parameter);
        return productMapper.entityToDto(product);
    }

    @Operation(summary = "Find by category",
            description = "Find all products by store category")
    @GetMapping(value = "/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> findByCategory(@Parameter(description = "Category") @RequestParam("category") String category) {
        List<Product> products = productService.findAllByCategory(category);
        return productMapper.entityToDto(products);
    }


}
