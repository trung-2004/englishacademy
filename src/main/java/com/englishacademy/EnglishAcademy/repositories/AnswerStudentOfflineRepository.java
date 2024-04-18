package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.AnswerStudentOffline;
import com.englishacademy.EnglishAcademy.entities.QuestionTestOffline;
import com.englishacademy.EnglishAcademy.entities.TestOfflineStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerStudentOfflineRepository extends JpaRepository<AnswerStudentOffline, Long> {
    AnswerStudentOffline findByTestOfflineStudentAndQuestionTestOffline(TestOfflineStudent testOfflineStudent, QuestionTestOffline questionTestOffline);
}
