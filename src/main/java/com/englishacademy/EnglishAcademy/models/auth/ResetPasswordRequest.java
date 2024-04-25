package com.englishacademy.EnglishAcademy.models.auth;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
