package com.solvd.onlinestore.domain;

import com.solvd.onlinestore.domain.product.Product;
import lombok.Data;

@Data
public class OrderPoint {

    private Long id;
    private Order order;
    private Product product;

}
