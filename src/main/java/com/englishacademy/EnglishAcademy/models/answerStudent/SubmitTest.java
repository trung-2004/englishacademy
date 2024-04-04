package com.englishacademy.EnglishAcademy.models.answerStudent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmitTest {
    private int time;
    private List<CreateAnswerStudent> createAnswerStudentList;
}
