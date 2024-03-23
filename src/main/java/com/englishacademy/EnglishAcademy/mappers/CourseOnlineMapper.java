package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseOnlineMapper {
    @Autowired
    private TopicOnlineMapper topicOnlineMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private ReviewRepository reviewRepository;

    public CourseOnlineDTO toCourseOnlineDTO(CourseOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        CourseOnlineDTO courseOnlineDTO = CourseOnlineDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .image(model.getImage())
                .price(model.getPrice())
                .description(model.getDescription())
                .level(model.getLevel())
                .language(model.getLanguage())
                .status(model.getStatus())
                .star(model.getStar())
                .totalReview(reviewRepository.findAllByCourseOnline(model).size())
                .trailer(model.getTrailer())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return courseOnlineDTO;
    }

    public CourseOnlineDetail toCourseOnlineDetail(CourseOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        // Map từ topicOnlines sang TopicOnlineDetail và lưu vào danh sách
        List<TopicOnlineDetail> topicOnlineDetails = model.getTopicOnlines().stream()
                .map(topicOnlineMapper::toTopicOnlineDetail)
                .collect(Collectors.toList());

        // Sắp xếp danh sách theo thứ tự mong muốn (ví dụ: theo id)
        topicOnlineDetails.sort(Comparator.comparingInt(TopicOnlineDetail::getOrderTop));

        List<ReviewDTO> reviewDTOS = reviewRepository.findAllByCourseOnline(model).stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());

        reviewDTOS.sort(Comparator.comparingLong(ReviewDTO::getId));


        CourseOnlineDetail courseOnlineDetail = CourseOnlineDetail.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .image(model.getImage())
                .price(model.getPrice())
                .description(model.getDescription())
                .level(model.getLevel())
                .language(model.getLanguage())
                .status(model.getStatus())
                .star(model.getStar())
                .reviewList(reviewDTOS)
                .trailer(model.getTrailer())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .topicOnlineDetailList(topicOnlineDetails)
                .build();

        return courseOnlineDetail;
    }
}
