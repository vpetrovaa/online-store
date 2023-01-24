package com.solvd.onlinestore.web.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Schema(description = "Response parameters, generated after authentication")
public class JwtTokenDto {

    @Schema(description = "Expiration time of access token")
    private Long expiration;

    @Schema(description = "Generated access token")
    private String accessToken;

    @Schema(description = "Generated refresh token")
    private String refreshToken;

}
