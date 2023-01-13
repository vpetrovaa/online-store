package com.solvd.onlinestore.web.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solvd.onlinestore.domain.product.Product;
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
public class ProductDto {

    private Long id;

    @NotNull(message = "Category cant be null")
    private Product.Category category;

    @Size(min = 7, max = 7, message = "Article must be 7 symbols")
    @NotBlank(message = "Article cant be null")
    private String article;

    @Size(min = 1, max = 50, message = "Model must be from 1 to 50 symbols")
    @NotBlank(message = "Model cant be null")
    private String model;

    @Size(min = 20, message = "Description should be more than 20 symbols")
    @NotBlank(message = "Description cant be null")
    private String description;

    @DecimalMin(value = "0.1", message = "Cost should be more than 0")
    @Digits(integer = 3, fraction = 2)
    @NotNull(message = "Cost cant be null")
    private BigDecimal cost;

    @JsonIgnore
    @Min(value = 0, message = "Amount should me 0 or more than 0")
    @NotNull(message = "Amount cant be null")
    private int amount;

}
