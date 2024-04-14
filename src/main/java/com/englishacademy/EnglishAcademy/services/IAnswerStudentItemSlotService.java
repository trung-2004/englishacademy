package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answerStudent.ScoreAnswerStudentItemSlot;

public interface IAnswerStudentItemSlotService {
    void save(CreateAnswerStudentItemSlot model, Long studentId);

    void scoreAnswer(ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot, Long studentId);
}
