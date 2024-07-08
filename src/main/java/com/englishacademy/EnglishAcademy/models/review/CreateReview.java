package com.englishacademy.EnglishAcademy.models.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReview {
    private String courseOnlineSlug;
    private String message;
    private Double score;
}
