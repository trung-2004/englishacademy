package com.englishacademy.EnglishAcademy.models.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
