//package com.solvd.onlinestore.repository.jdbc;
//
//import com.solvd.onlinestore.domain.Warehouse;
//import com.solvd.onlinestore.domain.exception.SqlException;
//import com.solvd.onlinestore.domain.product.Product;
//import com.solvd.onlinestore.repository.DataSourceConfig;
//import com.solvd.onlinestore.repository.WarehouseRepository;
//import lombok.RequiredArgsConstructor;
//
//import java.sql.*;
//import java.util.Objects;
//
//@RequiredArgsConstructor
//public class WarehouseRepositoryImpl implements WarehouseRepository {
//
//    private static final String CREATE_QUERY = "insert into warehouses(product_id, amount) values(?, ?);";
//
//    private final DataSourceConfig dataSource;
//
//    @Override
//    public void create(Warehouse warehouse, Product product) {
//        try {
//            Connection conn = Objects.requireNonNull(dataSource.getConnection());
//            try (PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
//                ps.setLong(1, product.getId());
//                ps.setInt(2, warehouse.getAmount());
//                ps.executeUpdate();
//                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        warehouse.setId(generatedKeys.getLong(1));
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            throw new SqlException("Exception in creating warehouse with product_id " + warehouse.getProduct().getId());
//        }
//    }
//
//}
