package com.solvd.onlinestore.service.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "jwt.properties")
public class JwtProperties {

    private final Long access;
    private final Long refresh;
    private final String secret;

}
