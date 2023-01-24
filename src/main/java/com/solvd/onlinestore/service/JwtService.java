package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.jwt.JwtToken;
import com.solvd.onlinestore.web.security.JwtUser;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    JwtUser extractAllClaims(String token);

    JwtToken generateJwtToken(User user);

}
