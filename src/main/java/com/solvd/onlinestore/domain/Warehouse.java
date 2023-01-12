package com.solvd.onlinestore.domain;

import com.solvd.onlinestore.domain.product.Product;
import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    private int amount;
    private Product product;

}
