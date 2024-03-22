package com.englishacademy.EnglishAcademy.models.courseOnline;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCourseOnline {
    private String name;

    private MultipartFile image;

    private Double price;

    private String description;

    private Integer level;

    private String language;

    private String trailer;
}
