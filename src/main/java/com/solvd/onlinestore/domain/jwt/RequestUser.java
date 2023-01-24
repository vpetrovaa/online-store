package com.solvd.onlinestore.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestUser {

    private String username;
    private String password;

}
