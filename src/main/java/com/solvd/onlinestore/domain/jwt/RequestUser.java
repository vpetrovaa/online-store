package com.solvd.onlinestore.domain.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestUser {

    private String username;
    private String password;

}
