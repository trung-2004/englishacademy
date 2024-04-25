package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.models.courseOnline.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.review.CreateReview;

public interface IReviewService {
    ReviewDTO create(CreateReview model, Long studentId);
}
