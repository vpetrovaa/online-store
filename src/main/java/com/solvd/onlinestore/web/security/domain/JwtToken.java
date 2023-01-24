package com.solvd.onlinestore.web.security.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtToken {

    private Long expiration;
    private String accessToken;
    private String refreshToken;

}
