package com.solvd.onlinestore.domain.mapper;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasketMapper {

    @SneakyThrows
    public static List<Basket> mapForFindAll(ResultSet rs){
        List<Basket> baskets = new ArrayList<>();
        while(rs.next()){
            Basket basket = new Basket();
            basket.setId(rs.getLong("basket_id"));
            Product product = ProductMapper.mapByIdCategoryModelCost(rs);
            User user = UserMapper.mapByIdNameSurname(rs);
            basket.setProduct(product);
            basket.setUser(user);
            baskets.add(basket);
        }
        return baskets;
    }

    @SneakyThrows
    public static Optional<Basket> mapForFindOne(ResultSet rs){
        if(rs.next()){
            Basket basket = new Basket();
            basket.setId(rs.getLong("basket_id"));
            Product product = ProductMapper.mapByIdCategoryModelCost(rs);
            User user = UserMapper.mapByIdNameSurname(rs);
            basket.setProduct(product);
            basket.setUser(user);
            return Optional.of(basket);
        }
        return Optional.empty();
    }

}
