package com.englishacademy.EnglishAcademy.dtos.courseOnline;

import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOnlineDetail {
    private Long id;
    private String name;

    private String slug;

    private String image;

    private Double price;

    private String description;

    private Integer level;

    private String language;

    private Integer status;

    private Double star;

    private List<ReviewDTO> reviewList;

    private String duration;

    private String trailer;

    private List<TopicOnlineDetail> topicOnlineDetailList;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
