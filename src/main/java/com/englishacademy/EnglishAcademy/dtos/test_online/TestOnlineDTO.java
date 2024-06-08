package com.englishacademy.EnglishAcademy.dtos.test_online;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestOnlineDTO {

    private Long id;
    private String title;
    private String slug;
    private Integer type;
    private Integer pastMark;
    private Integer totalMark;
    private String description;
    private Integer totalQuestion;
    private Integer time;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
