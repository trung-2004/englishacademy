package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDTO toReviewDTO(Review model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(model.getId())
                .courseOnlineId(model.getCourseOnline().getId())
                .studentId(model.getStudent().getId())
                .score(model.getScore())
                .message(model.getMessage())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return reviewDTO;
    }
}
