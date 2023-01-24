package com.solvd.onlinestore.web.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Schema(description = "Information about refresh token, that should be regenerated")
public class RefreshDto {

    @Schema(description = "Refresh token, that is needed to update both tokens")
    private String refresh;

}
