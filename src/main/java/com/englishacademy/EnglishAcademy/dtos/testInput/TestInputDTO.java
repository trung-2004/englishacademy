package com.englishacademy.EnglishAcademy.dtos.testInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestInputDTO {
    private Long id;
    private String title;
    private String slug;
    private Integer type;
    private String description;
    private Integer totalQuestion;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
