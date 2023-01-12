package com.solvd.onlinestore.domain;

import com.solvd.onlinestore.domain.product.Product;
import lombok.Data;

@Data
public class Basket {

    private Long id;
    private Product product;
    private User user;

}
