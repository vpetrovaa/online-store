package com.solvd.onlinestore.web.dto;

import com.solvd.onlinestore.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Information about user and his account")
public class UserDto {

    @Schema(description = "Unique id")
    private Long id;

    @Schema(description = "Name")
    @Size(min = 3, max = 30, message = "Name must be from 3 to 30 symbols")
    @NotBlank(message = "Name cant be empty")
    private String name;

    @Schema(description = "Surname")
    @Size(min = 3, max = 30, message = "Surname must be from 3 to 30 symbols")
    @NotBlank(message = "Surname cant be empty")
    private String surname;

    @Schema(description = "Unique email(username)")
    @Email(message = "Enter your email")
    @NotBlank(message = "Email cant be empty")
    private String email;

    @Schema(description = "Phone number")
    @Size(min = 12, max = 12, message = "Phone must be 12 symbols")
    @NotBlank(message = "Phone cant be empty")
    private String phone;

    @Schema(description = "Role(ADMIN or USER)")
    private User.Role role;

    @Schema(description = "Password")
    @Size(min = 10, max = 30, message = "Password must be from 10 to 30 symbols")
    @NotBlank(message = "Password cant be empty")
    private String password;

    @Schema(description = "Date and time of registration")
    private LocalDateTime registrationTime;

}
