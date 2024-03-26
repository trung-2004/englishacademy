package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDetail;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudent;

import java.util.List;

public interface ITestInputService {
    List<TestInputDTO> findAllToiec();
    List<TestInputDTO> findAllIelts();
    TestInputDetail getdetailTest(String slug);

    void submitTest(String slug, Long studentId, List<CreateAnswerStudent> answersForStudents);
}
