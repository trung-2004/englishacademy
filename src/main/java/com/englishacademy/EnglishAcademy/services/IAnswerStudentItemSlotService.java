package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answerStudent.ScoreAnswerStudentItemSlot;

public interface IAnswerStudentItemSlotService {
    AnswerStudentItemSlot save(CreateAnswerStudentItemSlot model, Long studentId);

    AnswerStudentItemSlot scoreAnswer(ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot, Long studentId);
}
