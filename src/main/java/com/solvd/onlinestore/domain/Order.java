package com.solvd.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private User user;
    private BigDecimal amount;
    private DeliveryEnum deliveryMethod;
    private PaymentEnum paymentMethod;
    private LocalDateTime orderDate;
    private StatusEnum status;
    private String address;
    private LocalDate deliveryDate;

    public enum DeliveryEnum {

        PICKUP("Pickup"),
        COURIER("Courier");

        private final String deliveryMethod;

        DeliveryEnum(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        @JsonValue
        public String getDeliveryMethod() {
            return deliveryMethod;
        }

    }

    public enum PaymentEnum {

        CARD("Card"),
        CASH("Cash");

        private final String paymentMethod;

        PaymentEnum(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        @JsonValue
        public String getPaymentMethod() {
            return paymentMethod;
        }

    }

    public enum StatusEnum {

        TRUE("TRUE"),
        FALSE("FALSE");

        private final String status;

        StatusEnum(String status) {
            this.status = status;
        }

        @JsonValue
        public String getStatus() {
            return status;
        }

    }

}
