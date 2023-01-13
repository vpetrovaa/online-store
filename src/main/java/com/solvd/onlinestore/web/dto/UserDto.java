package com.solvd.onlinestore.web.dto;

import com.solvd.onlinestore.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    @Size(min = 3, max = 30, message = "Name must be from 3 to 30 symbols")
    @NotBlank(message = "Name cant be empty")
    private String name;

    @Size(min = 3, max = 30, message = "Surname must be from 3 to 30 symbols")
    @NotBlank(message = "Surname cant be empty")
    private String surname;

    @Email(message = "Enter your email")
    @NotBlank(message = "Email cant be empty")
    private String email;

    @Size(min = 12, max = 12, message = "Phone must be 12 symbols")
    @NotBlank(message = "Phone cant be empty")
    private String phone;

    private User.Role role;

    @Size(min = 10, max = 30, message = "Password must be from 10 to 30 symbols")
    @NotBlank(message = "Password cant be empty")
    private String password;

    private LocalDateTime registrationTime;

}
