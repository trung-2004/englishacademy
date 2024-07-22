package com.englishacademy.EnglishAcademy.dtos.question_test_online;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionTestOnlineDTOResponse {
    private Long id;
    private String title;
    private String audiomp3;
    private String image;
    private String paragraph;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
    private Integer type;
    private Integer part;
    private Integer orderTop;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
