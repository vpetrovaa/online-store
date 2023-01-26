package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.service.JwtService;
import com.solvd.onlinestore.service.property.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    JwtService jwtService;

    @Mock
    JwtProperties jwtProperties;

    @BeforeEach
    void setUp(){
        jwtService = new JwtServiceImpl(jwtProperties);
    }

    @Test
    void generateAccessToken() {
        long access = 5L;
        User user = generateUser();
        when(jwtProperties.getAccess()).thenReturn(access);
        when(jwtProperties.getSecret()).thenReturn(getSecret());
        String accessToken = jwtService.generateAccessToken(user);
        assertNotNull(accessToken, "Assert generation of access token");
        verify(jwtProperties, times(1)).getAccess();
        verify(jwtProperties, times(1)).getSecret();
    }

    @Test
    void generateRefreshToken() {
        long refresh = 30L;
        when(jwtProperties.getRefresh()).thenReturn(refresh);
        when(jwtProperties.getSecret()).thenReturn(getSecret());
        User user = generateUser();
        String refreshToken = jwtService.generateRefreshToken(user);
        assertNotNull(refreshToken, "Assert generation of refresh token");
        verify(jwtProperties, times(1)).getRefresh();
        verify(jwtProperties, times(1)).getSecret();
    }

    private User generateUser() {
        return new User(1L, "Katya", "Ivanova", "katya@mail.ru", "375296625296", User.Role.ROLE_USER,
                "helloWorld123", LocalDateTime.now());
    }

    private String getSecret(){
        return "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDlslslslslVV5IkIxBMzr7OEHXEHd37w";
    }

}