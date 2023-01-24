package com.solvd.onlinestore.web.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solvd.onlinestore.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Information about product from catalog")
public class ProductDto {

    @Schema(description = "Unique id")
    private Long id;

    @Schema(description = "Furniture category")
    @NotNull(message = "Category cant be null")
    private Product.Category category;

    @Schema(description = "Unique article, that consists of 7 symbols")
    @Size(min = 7, max = 7, message = "Article must be 7 symbols")
    @NotBlank(message = "Article cant be null")
    private String article;

    @Schema(description = "Unique name of model")
    @Size(min = 1, max = 50, message = "Model must be from 1 to 50 symbols")
    @NotBlank(message = "Model cant be null")
    private String model;

    @Schema(description = "Description")
    @Size(min = 20, message = "Description should be more than 20 symbols")
    @NotBlank(message = "Description cant be null")
    private String description;

    @Schema(description = "Cost of 1 thing")
    @DecimalMin(value = "0.1", message = "Cost should be more than 0")
    @Digits(integer = 3, fraction = 2)
    @NotNull(message = "Cost cant be null")
    private BigDecimal cost;

    @Schema(description = "Goods remaining")
    @JsonIgnore
    @Min(value = 0, message = "Amount should me 0 or more than 0")
    @NotNull(message = "Amount cant be null")
    private int amount;

}
