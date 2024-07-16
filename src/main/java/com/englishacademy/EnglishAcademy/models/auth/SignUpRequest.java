package com.englishacademy.EnglishAcademy.models.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "Full name is mandatory")
    private String fullname;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 255, message = "Password must be between 3 and 255 characters")
    private String password;
}
