package com.solvd.onlinestore.web.dto;

import com.solvd.onlinestore.domain.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Information about order")
public class OrderDto {

    @Schema(description = "Unique id")
    private Long id;

    @Schema(description = "Owner")
    private UserDto user;

    @Schema(description = "Summary cost of products")
    @Digits(integer = 3, fraction = 2, message = "Integer part should be <= 3 symbols, fraction part <=2")
    private BigDecimal amount;

    @Schema(description = "Delivery method(COURIER or PICKUP)")
    @NotNull(message = "Delivery cant be null")
    private Order.Delivery deliveryMethod;

    @Schema(description = "Payment method(CASH or CARD)")
    @NotNull(message = "Payment cant be null")
    private Order.Payment paymentMethod;

    @Schema(description = "Date of creation")
    private LocalDateTime orderDate;

    @Schema(description = "Confirming status(TRUE or FALSE )")
    private String status;

    @Schema(description = "Address for delivering")
    @Size(min = 10, max = 80, message = "Address must be from 10 to 80 symbols")
    @NotBlank(message = "Address cant be empty")
    private String address;

    @Schema(description = "Date for delivering")
    @NotNull(message = "Delivery date cant be null")
    private LocalDate deliveryDate;

}
