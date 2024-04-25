package com.englishacademy.EnglishAcademy.models.auth;

import lombok.Data;

@Data
public class SignUpRequest {
    private String fullname;
    private String email;
    private String password;
}
