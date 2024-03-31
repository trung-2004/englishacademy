package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnlineStudent.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudent;

import java.util.List;

public interface ITestOnlineService {
    TestOnlineDetail getdetailTest(String slug, Long studentId);

    void submitTest(String slug, Long studentId, List<CreateAnswerStudent> answersForStudents);

    TestOnlineStudentDTO getresultTest(String code);
}
