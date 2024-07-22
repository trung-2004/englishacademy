package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.test_online_student.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;
import com.englishacademy.EnglishAcademy.models.test_online.CreateTestOnline;
import com.englishacademy.EnglishAcademy.models.test_online.EditTestOnline;

import java.util.List;

public interface TestOnlineService {
    TestOnlineDetail getdetailTest(String slug, Long studentId);
    String submitTest(String slug, Long studentId, SubmitTest submitTest);
    TestOnlineStudentDTO getresultTest(String code);
    void saveTestOnline(CreateTestOnline createTestOnline);
    TestOnlineDTO edit(EditTestOnline editTestOnline);
    void delete(List<Long> ids);
    TestOnlineDTO getBySlug(String slug);
    TestOnlineDetailResponse getdetailTestByUser(String slug, Long id);
}
