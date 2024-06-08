package com.englishacademy.EnglishAcademy.models.question_test_input;

import lombok.Getter;

@Getter
public class CreateQuestionTestInput {
    private String title;
    private String sesstionId;
    private String audiomp3;
    private String image;
    private String paragraph;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctanswer;
    private Integer type;
    private Integer part;
    private Integer orderTop;
}
