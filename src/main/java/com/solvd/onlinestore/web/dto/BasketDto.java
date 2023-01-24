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
@Schema(description = "Information about product, added to basket")
public class BasketDto {

    @Schema(description = "Unique id")
    private Long id;

    @Schema(description = "Owner of basket")
    private UserDto user;

    @Schema(description = "Product, that user added")
    private ProductDto product;

}
