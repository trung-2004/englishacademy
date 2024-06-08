package com.englishacademy.EnglishAcademy.models.course_online;

import lombok.Data;

@Data
public class EditCourseOnline {
    private Long id;
    private String name;
    private String image;
    private Double price;
    private String description;

    private Integer level;

    private String language;

    private String trailer;
}
