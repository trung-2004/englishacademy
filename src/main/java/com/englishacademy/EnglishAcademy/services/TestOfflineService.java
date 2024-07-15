package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.test_offline.TestOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_offline_student.TestOfflineStudentDTO;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOfflineSessionDetailResult;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerOfflineStudent;

import java.util.List;

public interface TestOfflineService {
    TestOfflineDetail getdetailTest(String slug, Long studentId);
    void submitTest(String slug, Long studentId, List<CreateAnswerOfflineStudent> createAnswerOfflineStudentList);
    List<TestOfflineSessionDetailResult> getdetailToScoreTest(String code);
    List<TestOfflineStudentDTO> getListScore(Long classId, String slug);
}
