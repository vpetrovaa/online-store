package com.solvd.onlinestore.domain.product;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private Category category;
    private String article;
    private String model;
    private String description;
    private BigDecimal cost;
    private String warehouse;
    private int amount;

    public enum Category {

        STOCKS("STOCKS"),
        TABLES("TABLES"),
        CHAIRS("CHAIRS"),
        WARDROBES("WARDROBES"),
        SOFAS("SOFAS");

        private final String category;

        Category(String category) {
            this.category = category;
        }

        @JsonValue
        public String getCategory() {
            return category;
        }

    }

}


