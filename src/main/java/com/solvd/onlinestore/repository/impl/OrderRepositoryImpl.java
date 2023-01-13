package com.solvd.onlinestore.repository.impl;

import com.solvd.onlinestore.config.DataSourceConfig;
import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.repository.OrderRepository;
import com.solvd.onlinestore.repository.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final DataSourceConfig dataSource;
    private static final String FIND_BY_ID_QUERY = """
            select orders.id as order_id, orders.amount as order_amount, orders.delivery_method as order_delivery_method,
            orders.payment_method as order_payment_method, orders.order_date as order_date, orders.status as order_status,
            orders.address as order_address, orders.delivery_date as order_delivery_date,
            users.id as user_id, users.email as user_email, users.name as user_name
            from store.orders 
            left join store.users on (orders.user_id = users.id)
            where orders.id = ?;
            """;
    private static final String FIND_BY_STATUS_QUERY = """
            select orders.id as order_id, orders.amount as order_amount, orders.delivery_method as order_delivery_method,
            orders.payment_method as order_payment_method, orders.order_date as order_date, orders.status as order_status,
            orders.address as order_address, orders.delivery_date as order_delivery_date,
            users.id as user_id, users.email as user_email, users.name as user_name
            from store.orders 
            left join store.users on (orders.user_id = users.id)
            where status = ?;
            """;
    private static final String CREATE_QUERY = "insert into orders(user_id, amount, delivery_method, payment_method, order_date, status, " +
            "address, delivery_date) values(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "update orders set status = ? where id = ?;";

    @Override
    public void save(Order order, Long userId, BigDecimal amount) {
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
                ps.setLong(1, userId);
                ps.setBigDecimal(2, amount);
                ps.setString(3, order.getDeliveryMethod().getDeliveryMethod().toUpperCase());
                ps.setString(4, order.getPaymentMethod().getPaymentMethod().toUpperCase());
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "false");
                ps.setString(7, order.getAddress());
                ps.setDate(8, Date.valueOf(order.getDeliveryDate()));
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in creating order with user_id - " + order.getUser().getId());
        }
    }

    @Override
    public List<Order> findAllByStatus(String status) {
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_BY_STATUS_QUERY)){
                ps.setString(1, status);
                try(ResultSet rs = ps.executeQuery()) {
                    return OrderMapper.mapForFindAll(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SqlException("Exception in getting orders with status - " + status);
        }
    }

    @Override
    public void updateStatus(Order order) {
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY)){
                ps.setString(1, order.getStatus().getStatus().toUpperCase());
                ps.setLong(2, order.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in confirming order with id - " + order.getId());
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_QUERY)){
                ps.setLong(1, id);
                try(ResultSet rs = ps.executeQuery()) {
                    return OrderMapper.mapForFindOne(rs);
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in finding order with id " + id);
        }
    }

}
