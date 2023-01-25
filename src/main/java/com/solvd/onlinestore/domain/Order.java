package com.solvd.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private User user;
    private BigDecimal amount;
    private Delivery deliveryMethod;
    private Payment paymentMethod;
    private LocalDateTime orderDate;
    private Status status;
    private String address;
    private LocalDate deliveryDate;

    public enum Delivery {

        PICKUP("Pickup"),
        COURIER("Courier");

        private final String deliveryMethod;

        Delivery(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        @JsonValue
        public String getDeliveryMethod() {
            return deliveryMethod;
        }

    }

    public enum Payment {

        CARD("Card"),
        CASH("Cash");

        private final String paymentMethod;

        Payment(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        @JsonValue
        public String getPaymentMethod() {
            return paymentMethod;
        }

    }

    public enum Status {

        TRUE("TRUE"),
        FALSE("FALSE");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        @JsonValue
        public String getStatus() {
            return status;
        }

    }

}
