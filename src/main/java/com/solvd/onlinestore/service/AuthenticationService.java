package com.solvd.onlinestore.service;

import com.solvd.onlinestore.domain.jwt.JwtToken;
import com.solvd.onlinestore.domain.jwt.Refresh;
import com.solvd.onlinestore.domain.jwt.RequestUser;

public interface AuthenticationService {

    JwtToken login(RequestUser requestDto);

    JwtToken refreshToken(Refresh refresh);

}
