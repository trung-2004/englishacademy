package com.englishacademy.EnglishAcademy.models.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequestNew {
    @NotBlank(message = "Full name is mandatory")
    private String fullname;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
}
