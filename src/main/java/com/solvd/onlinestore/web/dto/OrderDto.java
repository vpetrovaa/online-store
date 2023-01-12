package com.solvd.onlinestore.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solvd.onlinestore.domain.Order;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private Long id;

    private UserDto user;

    @Digits(integer = 3, fraction = 2, message = "Integer part should be <= 3 symbols, fraction part <=2")
    private BigDecimal amount;

    @NotNull(message = "Delivery cant be null")
    private Order.DeliveryEnum deliveryMethod;

    @NotNull(message = "Payment cant be null")
    private Order.PaymentEnum paymentMethod;

    private LocalDateTime orderDate;

    private String status;
    @Size(min = 10, max = 80, message = "Address must be from 10 to 80 symbols")
    @NotNull(message = "Address cant be null")
    @NotBlank(message = "Address cant be empty")
    private String address;

    @NotNull(message = "Delivery date cant be null")
    private LocalDate deliveryDate;

}
