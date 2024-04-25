package com.englishacademy.EnglishAcademy.models.auth;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
