package com.solvd.onlinestore.web.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSearchParameterDto {

    @Size(min = 1, max = 50, message = "Parameter should be from 1 to 50 symbols")
    private String parameter;

}
