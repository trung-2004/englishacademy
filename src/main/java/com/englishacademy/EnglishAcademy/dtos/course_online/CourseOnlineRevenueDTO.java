package com.englishacademy.EnglishAcademy.dtos.course_online;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOnlineRevenueDTO {
    private Long courseId;
    private String courseName;
    private String courseSlug;
    private String image;
    private Double price;
    private Double star;
    private Integer status;
    private String trailer;
    private Long category_id;
    private Double totalRevenue;
}
