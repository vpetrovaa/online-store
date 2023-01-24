package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.AuthenticationException;
import com.solvd.onlinestore.web.security.domain.JwtToken;
import com.solvd.onlinestore.web.security.domain.Refresh;
import com.solvd.onlinestore.domain.jwt.RequestUser;
import com.solvd.onlinestore.service.AuthenticationService;
import com.solvd.onlinestore.service.UserService;
import com.solvd.onlinestore.service.JwtService;
import com.solvd.onlinestore.web.security.JwtUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtToken login(RequestUser requestDto) {
        final User user = userService.findByEmail(requestDto.getUsername());
        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        return jwtService.generateJwtToken(user);
    }

    @Override
    public JwtToken refreshToken(Refresh refresh) {
        String refreshToken = refresh.getRefresh();
        JwtUser jwtUser = jwtService.extractAllClaims(refreshToken);
        User user = userService.findByEmail(jwtUser.getEmail());
        JwtToken newToken = new JwtToken();
        String newAccessToken = jwtService.generateAccessToken(user);
        newToken.setAccessToken(newAccessToken);
        newToken.setRefreshToken(jwtService.generateRefreshToken(user));
        newToken.setExpiration(jwtService.extractClaim(newAccessToken, Claims:: getExpiration).getTime());
        return newToken;
    }

}
