package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.models.review.CreateReview;

public interface ReviewService {
    ReviewDTO create(CreateReview model, Long studentId);
}
