package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.testOffline.TestOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.testSession.TestOfflineSessionDetailResult;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerOfflineStudent;
import com.englishacademy.EnglishAcademy.models.answerStudent.SubmitTest;

import java.util.List;

public interface ITestOfflineService {
    TestOfflineDetail getdetailTest(String slug, Long studentId);

    void submitTest(String slug, Long studentId, List<CreateAnswerOfflineStudent> createAnswerOfflineStudentList);

    List<TestOfflineSessionDetailResult> getdetailToScoreTest(String code);

    //TestOnlineStudentDTO getresultTest(String code);
}
