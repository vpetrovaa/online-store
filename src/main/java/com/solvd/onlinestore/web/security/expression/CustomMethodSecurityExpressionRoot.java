package com.solvd.onlinestore.web.security.expression;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.service.BasketService;
import com.solvd.onlinestore.service.OrderService;
import com.solvd.onlinestore.service.UserService;
import com.solvd.onlinestore.web.security.JwtUser;
import lombok.Setter;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

import java.util.List;

@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private BasketService basketService;
    private UserService userService;
    private OrderService orderService;

    private Authentication authentication;
    private Object filterObject;
    private Object returnObject;
    private Object target;


    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
        this.authentication = authentication;
    }

    public boolean hasPermissionToAddBasket(Long basketId){
        JwtUser user = (JwtUser) authentication.getPrincipal();
        List<Basket> baskets = basketService.findAllByUser(userService.findByEmail(user.getEmail()).getId());
        return !baskets.isEmpty() && baskets.get(0).getUser().getId().equals(basketId);
    }

    public boolean hasPermissionToFindAllBaskets(Long userId){
        JwtUser user = (JwtUser) authentication.getPrincipal();
        List<Basket> basketsByPrincipal = basketService.findAllByUser(userService.findByEmail(user.getEmail()).getId());
        List<Basket> basketsById = basketService.findAllByUser(userId);
        return basketsById.equals(basketsByPrincipal);
    }

    public boolean hasPermissionToConfirmOrder(Long orderId){
        List<Order> orders = orderService.findAllByStatus("FALSE");
        return orders.stream()
                .map(Order :: getId)
                .noneMatch(orderId::equals) && authentication.getAuthorities().contains(User.Role.ROLE_ADMIN);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }

}
