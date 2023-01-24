package com.solvd.onlinestore.web.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Request parameters(username, password), that are to authenticate")
public class RequestUserDto {

    @Schema(description = "Username parameter(email)")
    @NotBlank(message = "Email cant be null")
    @Email(message = "Enter Your Email")
    private String username;

    @Schema(description = "Password parameter")
    @NotBlank(message = "Password cant be null")
    @Size(min = 3, max = 30, message = "Password should be from 3 to 30 symbols")
    private String password;

}
