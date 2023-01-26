package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.AuthenticationException;
import com.solvd.onlinestore.domain.jwt.RequestUser;
import com.solvd.onlinestore.service.AuthenticationService;
import com.solvd.onlinestore.service.JwtService;
import com.solvd.onlinestore.service.UserService;
import com.solvd.onlinestore.web.security.JwtToken;
import com.solvd.onlinestore.web.security.JwtUser;
import com.solvd.onlinestore.web.security.Refresh;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp(){
        authenticationService = new AuthenticationServiceImpl(userService, bCryptPasswordEncoder, jwtService);
    }

    @Test
    void verifyLoginPassedTest() {
        RequestUser requestUser = generateRequestUser();
        JwtToken token = generateJwtToken();
        User user = generateUser();
        String username = "katya@mail.ru";
        String password = "helloWorld123";
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateJwtToken(any(User.class))).thenReturn(token);
        JwtToken tokenCreated = authenticationService.login(requestUser);
        assertEquals(token, tokenCreated, "Assert that token and tokenCreated are equals");
        verify(userService, times(1)).findByEmail(username);
        verify(bCryptPasswordEncoder, times(1)).matches(password, password);
        verify(jwtService, times(1)).generateJwtToken(user);
    }

    @Test
    void verifyLoginFailedTest() {
        RequestUser requestUser = generateRequestUser();
        User user = generateUser();
        String username = "katya@mail.ru";
        String password = "helloWorld123";
        when(userService.findByEmail(anyString())).thenReturn(user);
        assertThrows(AuthenticationException.class, () -> authenticationService.login(requestUser), "Asserts AuthenticationException");
        verify(userService, times(1)).findByEmail(username);
        verify(bCryptPasswordEncoder, times(1)).matches(password, password);
    }

    @Test
    void verifyRefreshTokenTest() {
        JwtUser jwtUser = generateJwtUser();
        User user = generateUser();
        JwtToken token = generateJwtToken();
        Refresh refresh = generateRefresh();
        String accessToken = "access";
        String refreshToken = "refresh";
        long expiration = 5L;
        String email = "katya@mail.ru";
        when(jwtService.extractAllClaims(anyString())).thenReturn(jwtUser);
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(jwtService.generateAccessToken(any(User.class))).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(refreshToken);
        when(jwtService.extractClaim(anyString(), any())).thenReturn(new Date(expiration));
        JwtToken tokenCreated = authenticationService.refreshToken(refresh);
        assertEquals(token, tokenCreated, "Assert that token and tokenCreated are equals");
        verify(jwtService, times(1)).extractAllClaims(refreshToken);
        verify(userService, times(1)).findByEmail(email);
        verify(jwtService, times(1)).generateAccessToken(user);
        verify(jwtService, times(1)).generateRefreshToken(user);
    }

    private RequestUser generateRequestUser(){
        return new RequestUser("katya@mail.ru", "helloWorld123");
    }

    private JwtToken generateJwtToken(){
        return new JwtToken(5L, "access", "refresh");
    }

    private Refresh generateRefresh(){
        return new Refresh("refresh");
    }

    private User generateUser(){
        return new User(1L, "Katya", "Ivanova", "katya@mail.ru", "375296625296", User.Role.ROLE_USER,
                "helloWorld123", LocalDateTime.now());
    }

    private JwtUser generateJwtUser(){
        return new JwtUser(true, "Katya", "katya@mail.ru", User.Role.ROLE_USER);
    }

}