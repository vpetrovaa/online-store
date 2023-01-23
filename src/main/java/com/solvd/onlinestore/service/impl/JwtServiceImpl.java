package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.jwt.JwtToken;
import com.solvd.onlinestore.service.JwtService;
import com.solvd.onlinestore.service.property.JwtProperties;
import com.solvd.onlinestore.web.security.JwtUser;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(User user){
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(jwtProperties.getAccess()).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .claim("role", user.getRole().getAuthority()) //
                .claim("name", user.getName())
                .claim("id", user.getId())
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(jwtProperties.getRefresh()).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    @Override
    public JwtUser extractAllClaims(String token) {
        Claims claims =  Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        final JwtUser user = new JwtUser();
        user.setRole(User.Role.valueOf(claims.get("role", String.class)));
        user.setName(claims.get("name", String.class));
        user.setEmail(claims.getSubject());
        return user;
    }

    @Override
    public JwtToken generateJwtToken(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        Long expiration = extractClaim(accessToken, Claims :: getExpiration).getTime();
        JwtToken jwtToken = new JwtToken();
        jwtToken.setExpiration(expiration);
        jwtToken.setAccessToken(accessToken);
        jwtToken.setRefreshToken(refreshToken);
        return jwtToken;
    }

}
