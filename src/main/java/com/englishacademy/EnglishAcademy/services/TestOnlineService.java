package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online_student.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;

public interface TestOnlineService {
    TestOnlineDetail getdetailTest(String slug, Long studentId);

    String submitTest(String slug, Long studentId, SubmitTest submitTest);

    TestOnlineStudentDTO getresultTest(String code);
}
