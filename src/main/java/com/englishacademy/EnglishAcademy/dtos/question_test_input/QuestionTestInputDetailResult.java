package com.englishacademy.EnglishAcademy.dtos.question_test_input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionTestInputDetailResult {
    private Long id;
    private String title;
    private String audiomp3;
    private String image;
    private String paragraph;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answerCorrect;
    private String answerForStudent;
    private boolean result;
    private Integer type;
    private Integer part;
    private Integer orderTop;
}
