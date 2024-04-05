package com.englishacademy.EnglishAcademy.dtos.courseOffline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOnlineResponse {
    private Long id;
    private String name;
    private String slug;
    private String image;
    private Double price;
    private Integer level;
    private String language;
    private boolean status;
    private Integer progress;
    private Timestamp createdDate;
}
