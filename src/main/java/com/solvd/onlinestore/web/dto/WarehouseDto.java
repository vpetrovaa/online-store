package com.solvd.onlinestore.web.dto;

import com.solvd.onlinestore.web.dto.product.ProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Information about products supplying")
public class WarehouseDto {

    @Schema(description = "Unique id")
    private Long id;

    @Schema(description = "Amount of product that should be supplied")
    @Min(value = 1, message = "Amount must be more than 0")
    @NotNull(message = "Amount cant be null")
    @Digits(integer = 3, fraction = 0, message = "You should specify integer value")
    private int amount;

    @Schema(description = "Product, which should be supplied")
    private ProductDto product;

}
