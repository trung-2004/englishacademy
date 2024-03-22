package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Review;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.mappers.ReviewMapper;
import com.englishacademy.EnglishAcademy.models.review.CreateReview;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ReviewRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOnlineRepository courseOnlineRepository;
    @Autowired
    private CourseOnlineStudentRepository courseOnlineStudentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ReviewDTO create(CreateReview model) {
        Optional<Student> studentOptional  = studentRepository.findById(model.getStudentId());
        Optional<CourseOnline> courseOnlineOptional = courseOnlineRepository.findById(model.getCourseOnlineId());
        if (!studentOptional.isPresent() || !courseOnlineOptional.isPresent()){
            throw new  RuntimeException("Not found");
        }

        Student student = studentOptional.get();
        CourseOnline courseOnline = courseOnlineOptional.get();

        CourseOnlineStudent courseOnlineStudentExsiting = courseOnlineStudentRepository.findByCourseOnlineAndStudent(courseOnline, student);

        if (courseOnlineStudentExsiting == null){
            throw new  RuntimeException("This course hasn't been purchased");
        }

        Review review = Review.builder()
                .courseOnline(courseOnline)
                .student(student)
                .score(model.getScore())
                .message(model.getMessage())
                .build();

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // Chỉ định múi giờ của bạn (ví dụ: Asia/Ho_Chi_Minh)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        Timestamp timestamp = Timestamp.from(zonedDateTime.toInstant());

        review.setCreatedBy("Demo");
        review.setCreatedDate(timestamp);
        review.setModifiedBy("Demo");
        review .setModifiedDate(timestamp);

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
