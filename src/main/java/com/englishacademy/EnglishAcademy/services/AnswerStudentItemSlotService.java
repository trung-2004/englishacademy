package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot.ListScore;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answer_student.ScoreAnswerStudentItemSlot;

public interface AnswerStudentItemSlotService {
    AnswerStudentItemSlot save(CreateAnswerStudentItemSlot model, Long studentId);
    AnswerStudentItemSlot scoreAnswer(ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot, Long studentId);
    ListScore listScore(String slug, Long id);
    AnswerStudentItemSlot scoreAnswerByTeacher(ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot, Long id);
}
