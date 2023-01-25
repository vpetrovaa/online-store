package com.solvd.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Role role;
    private String password;
    private LocalDateTime registrationTime;

    public enum Role implements GrantedAuthority {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER");

        @JsonValue
        private final String role;

        Role(String role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return role;
        }

    }

}
