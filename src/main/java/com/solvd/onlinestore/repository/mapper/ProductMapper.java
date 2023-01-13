package com.solvd.onlinestore.repository.mapper;

import com.solvd.onlinestore.domain.product.Product;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class ProductMapper {

    @SneakyThrows
    public static Product mapByIdCategoryModelCost(ResultSet rs) {
        Product product = new Product();
        product.setId(rs.getLong("product_id"));
        product.setCategory(Product.Category.valueOf(rs.getString("product_category").toUpperCase()));
        product.setModel(rs.getString("product_model"));
        product.setCost(rs.getBigDecimal("product_cost"));
        return product;
    }

}
