package com.solvd.onlinestore.repository;

import com.solvd.onlinestore.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderRepository {

    void create(@Param("order") Order order, @Param("userId") Long userId, @Param("amount") BigDecimal amount);

    List<Order> findAllByStatus(@Param("status") String status);

    void updateStatus(@Param("order") Order order);

    Optional<Order> findById(@Param("id") Long id);

}
