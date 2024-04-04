package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnlineStudent.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudent;
import com.englishacademy.EnglishAcademy.models.answerStudent.SubmitTest;

import java.util.List;

public interface ITestOnlineService {
    TestOnlineDetail getdetailTest(String slug, Long studentId);

    String submitTest(String slug, Long studentId, SubmitTest submitTest);

    TestOnlineStudentDTO getresultTest(String code);
}
