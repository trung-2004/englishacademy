package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudentItemSlot;

public interface IAnswerStudentItemSlotService {
    void save(CreateAnswerStudentItemSlot model, Long studentId);
}
