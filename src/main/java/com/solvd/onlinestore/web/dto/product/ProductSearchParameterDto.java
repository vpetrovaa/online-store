package com.solvd.onlinestore.web.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Criteria for product searching")
public class ProductSearchParameterDto {

    @Schema(description = "Parameter, compared with article or model of product")
    @Size(min = 1, max = 50, message = "Parameter should be from 1 to 50 symbols")
    @NotBlank(message = "Parameter cant be empty")
    private String parameter;

}
