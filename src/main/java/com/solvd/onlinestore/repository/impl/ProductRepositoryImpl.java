package com.solvd.onlinestore.repository.impl;

import com.solvd.onlinestore.config.DataSourceConfig;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final DataSourceConfig dataSource;
    private static final String FIND_BY_ID_QUERY = "select id, category, article, model, amount, description, cost " +
            "from products where id = ?";
    private static final String FIND_All_QUERY = "select id, category, article, model, amount, description, cost from products";
    private static final String CREATE_QUERY = "insert into products(category, article, model, amount, description, cost)" +
            "values(?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "delete from products where id = ?";
    private static final String FIND_ALL_BY_CATEGORY_QUERY = "select id, category, article, model, amount, description, cost" +
            " from store.products where category = ?";
    private static final String FIND_BY_MODEL_OR_ARTICLE_QUERY = "select id, category, article, model, amount, description, cost " +
            "from products where article = ? or model = ?";
    private static final String CHECK_EXIST_BY_ARTICLE_OR_MODEL_QUERY = "select id from products where article = ? or model = ?";
    private static final String UPDATE_QUERY = "update products set amount = ? where id = ?;";

    @Override
    public Optional<Product> findById(Long id){
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_QUERY)){
                ps.setLong(1,id);
                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Product product = new Product();
                        product.setId(id);
                        product.setCategory(Product.CategoryEnum.valueOf(rs.getString("category").toUpperCase()));
                        product.setArticle(rs.getString("article"));
                        product.setModel(rs.getString("model"));
                        product.setAmount(rs.getInt("amount"));
                        product.setDescription(rs.getString("description"));
                        product.setCost(rs.getBigDecimal("cost"));
                        return Optional.of(product);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in finding product with id " + id);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll(){
        List<Product> products = new ArrayList<>();
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(FIND_All_QUERY);
                ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setCategory(Product.CategoryEnum.valueOf(rs.getString("category").toUpperCase()));
                    product.setArticle(rs.getString("article"));
                    product.setModel(rs.getString("model"));
                    product.setAmount(rs.getInt("amount"));
                    product.setDescription(rs.getString("description"));
                    product.setCost(rs.getBigDecimal("cost"));
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in finding all products");
        }
    }

    @Override
    public void save(Product product){
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, product.getCategory().getCategory());
                ps.setString(2, product.getArticle());
                ps.setString(3, product.getModel());
                ps.setInt(4, product.getAmount());
                ps.setString(5, product.getDescription());
                ps.setBigDecimal(6, product.getCost());
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in saving product with article " + product.getArticle());
        }
    }

    @Override
    public void delete(Long id){
        try{
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try(PreparedStatement ps = conn.prepareStatement(DELETE_QUERY)){
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in delete method of product with id " + id);
        }
    }

    @Override
    public List<Product> findAllByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_ALL_BY_CATEGORY_QUERY)){
                ps.setString(1, category);
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setCategory(Product.CategoryEnum.valueOf(rs.getString("category").toUpperCase()));
                        product.setArticle(rs.getString("article"));
                        product.setModel(rs.getString("model"));
                        product.setAmount(rs.getInt("amount"));
                        product.setDescription(rs.getString("description"));
                        product.setCost(rs.getBigDecimal("cost"));
                        products.add(product);
                    }
                    return products;
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in finding products with category " + category);
        }
    }

    @Override
    public List<Product> findAllByCategoryOrdered(String category, String ordering) {
        String query = FIND_ALL_BY_CATEGORY_QUERY + " " + ordering;
        List<Product> products = new ArrayList<>();
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(query)){
                ps.setString(1, category);
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setCategory(Product.CategoryEnum.valueOf(rs.getString("category").toUpperCase()));
                        product.setArticle(rs.getString("article"));
                        product.setModel(rs.getString("model"));
                        product.setAmount(rs.getInt("amount"));
                        product.setDescription(rs.getString("description"));
                        product.setCost(rs.getBigDecimal("cost"));
                        products.add(product);
                    }
                    return products;
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in ordered finding of products with category " + category);
        }
    }

    @Override
    public Optional<Product> findByModelOrArticle(String parameter) {
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(FIND_BY_MODEL_OR_ARTICLE_QUERY)){
                ps.setString(1,parameter);
                ps.setString(2,parameter);
                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getLong("id"));
                        product.setCategory(Product.CategoryEnum.valueOf(rs.getString("category").toUpperCase()));
                        product.setArticle(rs.getString("article"));
                        product.setModel(rs.getString("model"));
                        product.setAmount(rs.getInt("amount"));
                        product.setDescription(rs.getString("description"));
                        product.setCost(rs.getBigDecimal("cost"));
                        return Optional.of(product);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in searching product by criteria " + parameter);
        }
        return Optional.empty();
    }

    @Override
    public void updateAmount(Product product) {
        Connection conn = Objects.requireNonNull(dataSource.getConnection());
        try{
            try(PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY)){
                ps.setInt(1, product.getAmount());
                ps.setLong(2, product.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in updating product with article " + product.getArticle());
        }
    }

    @Override
    public boolean isExistByArticleOrModel(String article, String model) {
        Long idFromDb = null;
        try{
            Connection conn = dataSource.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(CHECK_EXIST_BY_ARTICLE_OR_MODEL_QUERY)){
                ps.setString(1, article);
                ps.setString(2, model);
                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idFromDb = rs.getLong("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in checking product by article " + article + " and model " + model);
        }
        return idFromDb != null;
    }

}
