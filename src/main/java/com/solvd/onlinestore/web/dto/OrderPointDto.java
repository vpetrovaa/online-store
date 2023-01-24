package com.solvd.onlinestore.web.dto;

import com.solvd.onlinestore.web.dto.product.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Additional information about orders")
public class OrderPointDto {

    @Schema(description = "Unique id")
    private Long id;

    @Schema(description = "Product from order")
    private ProductDto product;

    @Schema(description = "Unique id of order")
    private OrderDto order;

}
