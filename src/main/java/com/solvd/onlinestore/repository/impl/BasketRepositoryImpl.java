package com.solvd.onlinestore.repository.impl;

import com.solvd.onlinestore.config.DataSourceConfig;
import com.solvd.onlinestore.domain.Basket;

import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.domain.mapper.BasketMapper;
import com.solvd.onlinestore.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryImpl implements BasketRepository {
    private final DataSourceConfig dataSource;
    private static final String CREATE_QUERY = "insert into baskets(product_id, user_id) values(?, ?);";
    private static final String DELETE_QUERY = "delete from baskets where id = ?;";
    private static final String FIND_ALL_BY_USER_QUERY = """
            select baskets.id as basket_id,
            users.id as user_id, users.email as user_email, users.name as user_name,
            products.id as product_id, products.category as product_category, products.model as product_model, products.cost as product_cost  
            from store.baskets 
            left join store.products on (baskets.product_id = products.id)
            left join store.users on (baskets.user_id = users.id)
            where user_id = ?;
            """;
    private static final String FIND_BY_ID_QUERY = """
            select baskets.id as basket_id,
            users.id as user_id, users.email as user_email, users.name as user_name,
            products.id as product_id, products.category as product_category, products.model as product_model, products.cost as product_cost  
            from store.baskets 
            left store.join products on (baskets.product_id = products.id)
            left store.join users on (baskets.user_id = users.id)
            where id = ?;
            """;
    private static final String IS_EXIST_BY_PRODUCT_AND_USER_QUERY = "select id from baskets where " +
            "product_id = ? and user_id = ?;";
    private static final String DELETE_BY_USER_QUERY = "delete from baskets where user_id = ?;";


    @Override
    public Basket save(Long productId, Long userId) {
        Basket basket = new Basket();
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, productId);
                ps.setLong(2, userId);
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        basket.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in creating new basket: product_id - " +
                    basket.getProduct().getId() + ", user_id - " + basket.getUser().getId());
        }
        return basket;
    }

    @Override
    public void deleteById(Long id) {
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(DELETE_QUERY)){
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in delete basket method where basket_id - " + id);
        }
    }

    @Override
    public List<Basket> findAllByUserId(Long id) {
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_ALL_BY_USER_QUERY)){
                ps.setLong(1, id);
                try(ResultSet rs = ps.executeQuery()) {
                    return BasketMapper.mapForFindAll(rs);
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in getting all baskets by user_id - " + id);
        }
    }


    @Override
    public Optional<Basket> findById(Long id) {
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_QUERY)){
                ps.setLong(1,id);
                try(ResultSet rs = ps.executeQuery()) {
                    return BasketMapper.mapForFindOne(rs);
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in getting basket by basket_id - " + id);
        }
    }

    @Override
    public boolean isExistByProductAndUser(Long productId, Long userId) {
        Long idFromDb = null;
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(IS_EXIST_BY_PRODUCT_AND_USER_QUERY)){
                ps.setLong(1,productId);
                ps.setLong(2,userId);
                try(ResultSet rs = ps.executeQuery()) {
                    if(rs.next()){
                        idFromDb = rs.getLong("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in getting basket by product_id - " + productId +
                    ", user_id - " + userId);
        }
        return idFromDb != null;
    }

    @Override
    public void deleteAllByUserId(Long id) {
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(DELETE_BY_USER_QUERY)){
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in delete method by basket id - " + id);
        }
    }

}
