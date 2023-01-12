package com.solvd.onlinestore.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPointDto {

    private Long id;

    private ProductDto product;

    private OrderDto order;

}
