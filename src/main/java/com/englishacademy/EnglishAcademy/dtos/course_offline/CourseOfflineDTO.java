package com.englishacademy.EnglishAcademy.dtos.course_offline;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOfflineDTO {

    private Long id;
    private String name;

    private String slug;

    private String image;

    private Double price;
    private String description;

    private Integer level;

    private String language;

    private Integer status;

    private String trailer;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
