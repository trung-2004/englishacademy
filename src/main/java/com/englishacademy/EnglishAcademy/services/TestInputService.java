package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.question_test_input.QuestionTestInputDetailResult;
import com.englishacademy.EnglishAcademy.dtos.test_input.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.test_input.TestInputDetail;
import com.englishacademy.EnglishAcademy.dtos.test_input_student.TestInputStudentDTO;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;
import com.englishacademy.EnglishAcademy.models.test_input.CreateTestInput;
import com.englishacademy.EnglishAcademy.models.test_input.EditTestInput;

import java.util.List;

public interface TestInputService {
    List<TestInputDTO> findAll();
    TestInputDetail getdetailTest(String slug);
    String submitTest(String slug, Long studentId, SubmitTest submitTest);
    TestInputStudentDTO getresultTest(String code);
    List<QuestionTestInputDetailResult> getresultDetailTest(String code);
    void saveTestInput(CreateTestInput createTestInput);
    TestInputDTO getBySLug(String slug);
    TestInputDTO edit(EditTestInput editTestInput);
    void delete(List<Long> ids);
}
