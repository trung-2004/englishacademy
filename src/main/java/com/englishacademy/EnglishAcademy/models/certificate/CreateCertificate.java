package com.englishacademy.EnglishAcademy.models.certificate;

import lombok.Data;

@Data
public class CreateCertificate {
    private Long userId;
    private String courseSlug;
}
