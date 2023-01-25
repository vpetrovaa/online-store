package com.solvd.onlinestore.web.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    private Long expiration;
    private String accessToken;
    private String refreshToken;

}
