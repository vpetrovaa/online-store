package com.solvd.onlinestore.repository.mapper;

import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderMapper {
    @SneakyThrows
    public static List<Order> mapForFindAll(ResultSet rs){
        List<Order> orders = new ArrayList<>();
        while(rs.next()){
            Order order = new Order();
            order.setId(rs.getLong("order_id"));
            order.setAmount(rs.getBigDecimal("order_amount"));
            order.setDeliveryMethod(Order.DeliveryEnum.valueOf(rs.getString("order_delivery_method").toUpperCase()));
            order.setPaymentMethod(Order.PaymentEnum.valueOf(rs.getString("order_payment_method").toUpperCase()));
            order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            order.setStatus(rs.getString("order_status"));
            order.setAddress(rs.getString("order_address"));
            order.setDeliveryDate(rs.getDate("order_delivery_date").toLocalDate());
            User user = UserMapper.mapByIdNameSurname(rs);
            order.setUser(user);
            orders.add(order);
        }
        return orders;
    }

    @SneakyThrows
    public static Optional<Order> mapForFindOne(ResultSet rs){
        if(rs.next()){
            Order order = new Order();
            order.setId(rs.getLong("order_id"));
            order.setAmount(rs.getBigDecimal("order_amount"));
            order.setDeliveryMethod(Order.DeliveryEnum.valueOf(rs.getString("order_delivery_method").toUpperCase()));
            order.setPaymentMethod(Order.PaymentEnum.valueOf(rs.getString("order_payment_method").toUpperCase()));
            order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            order.setStatus(rs.getString("order_status"));
            order.setAddress(rs.getString("order_address"));
            order.setDeliveryDate(rs.getDate("order_delivery_date").toLocalDate());
            User user = UserMapper.mapByIdNameSurname(rs);
            order.setUser(user);
            return Optional.of(order);
        }
        return Optional.empty();
    }

}
