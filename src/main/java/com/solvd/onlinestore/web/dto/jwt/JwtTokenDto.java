package com.solvd.onlinestore.web.dto.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtTokenDto {

    private Long expiration;
    private String accessToken;
    private String refreshToken;

}
