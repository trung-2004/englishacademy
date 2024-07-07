package com.englishacademy.EnglishAcademy.dtos.course_online;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOnlineDTO {

    private Long id;
    private String name;
    private String slug;
    private String image;
    private Double price;
    private Double star;
    private Integer totalReview;
    private String description;
    private Integer level;
    private String language;
    private Integer status;
    private String trailer;
    private Long category_id;
    private String categoryName;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
