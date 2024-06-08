package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Review;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ReviewMapper;
import com.englishacademy.EnglishAcademy.models.review.CreateReview;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ReviewRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final StudentRepository studentRepository;
    private final CourseOnlineRepository courseOnlineRepository;
    private final CourseOnlineStudentRepository courseOnlineStudentRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO create(CreateReview model, Long studentId) {
        Optional<Student> studentOptional  = studentRepository.findById(studentId);
        Optional<CourseOnline> courseOnlineOptional = courseOnlineRepository.findById(model.getCourseOnlineId());
        if (!studentOptional.isPresent() || !courseOnlineOptional.isPresent()){
            throw new AppException(ErrorCode.NOTFOUND);
        }

        Student student = studentOptional.get();
        CourseOnline courseOnline = courseOnlineOptional.get();

        CourseOnlineStudent courseOnlineStudentExsiting = courseOnlineStudentRepository.findByCourseOnlineAndStudent(courseOnline, student);

        if (courseOnlineStudentExsiting == null){
            throw new AppException(ErrorCode.COURSE_NOTPURCHASED);
        }

        Review review = Review.builder()
                .courseOnline(courseOnline)
                .student(student)
                .score(model.getScore())
                .message(model.getMessage())
                .build();

        review.setCreatedBy(student.getFullName());
        review.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        review.setModifiedBy(student.getFullName());
        review .setModifiedDate(new Timestamp(System.currentTimeMillis()));
        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findAllByCourseOnline(courseOnline);
        var totalReview = reviews.size();
        Double totalScore = 0.0;
        for (Review review1: reviews) {
            totalScore += review1.getScore();
        }
        Double star = totalScore/totalReview;
        courseOnline.setStar((double) Math.round(star * 10) / 10);

        courseOnlineRepository.save(courseOnline);
        return reviewMapper.toReviewDTO(review);
    }
}
