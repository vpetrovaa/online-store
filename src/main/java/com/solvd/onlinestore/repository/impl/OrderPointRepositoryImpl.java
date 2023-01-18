package com.solvd.onlinestore.repository.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.OrderPoint;
import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.repository.DataSourceConfig;
import com.solvd.onlinestore.repository.OrderPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class OrderPointRepositoryImpl implements OrderPointRepository {

    private static final String CREATE_QUERY = "insert into order_points(order_id, product_id) values(?, ?);";

    private final DataSourceConfig dataSource;

    @Override
    public void create(Basket basket, Order order) {
        OrderPoint orderPoint = new OrderPoint();
        orderPoint.setOrder(order);
        orderPoint.setProduct(basket.getProduct());
        try {
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try (PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, orderPoint.getOrder().getId());
                ps.setLong(2, orderPoint.getProduct().getId());
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderPoint.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in creating orderPoint with product_id - " +
                    orderPoint.getProduct().getId() + ", order_id - " + orderPoint.getOrder().getId());
        }
    }

}
