package com.solvd.onlinestore.domain;

import com.solvd.onlinestore.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

    private Long id;
    private Product product;
    private User user;

}
