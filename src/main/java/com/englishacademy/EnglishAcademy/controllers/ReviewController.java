package com.englishacademy.EnglishAcademy.controllers;


import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.models.review.CreateReview;
import com.englishacademy.EnglishAcademy.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @PostMapping("")
    ResponseEntity<ResponseObject> createReview(@RequestBody CreateReview model) {
        try {
            ReviewDTO reviewDTO = reviewService.create(model);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", reviewDTO)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }

}
