package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.AnswerStudent;
import com.englishacademy.EnglishAcademy.entities.QuestionTestInput;
import com.englishacademy.EnglishAcademy.entities.TestInputStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerStudentRepository extends JpaRepository<AnswerStudent, Long> {
    AnswerStudent findByQuestionTestInputAndTestInputStudent(QuestionTestInput questionTestInput, TestInputStudent testInputStudent);
}
