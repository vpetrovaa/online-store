package com.solvd.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private RoleEnum role;
    private String password;
    private LocalDateTime registrationTime;

    public enum RoleEnum {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER");

        private final String authority;

        RoleEnum(String authority) {
            this.authority = authority;
        }

        @JsonValue
        public String getAuthority() {
            return authority;
        }

    }

}
