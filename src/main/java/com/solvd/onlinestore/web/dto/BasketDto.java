package com.solvd.onlinestore.web.dto;

import com.solvd.onlinestore.web.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasketDto {

    private Long id;

    private UserDto user;

    private ProductDto product;

}
