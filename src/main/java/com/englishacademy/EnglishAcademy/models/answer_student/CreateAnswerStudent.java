package com.englishacademy.EnglishAcademy.models.answer_student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAnswerStudent {
    private String content;
    private Long questionId;
}
