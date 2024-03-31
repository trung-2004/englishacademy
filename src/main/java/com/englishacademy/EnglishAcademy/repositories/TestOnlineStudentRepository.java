package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.TestOnline;
import com.englishacademy.EnglishAcademy.entities.TestOnlineStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOnlineStudentRepository extends JpaRepository<TestOnlineStudent, Long> {
    TestOnlineStudent findByCode(String code);
    TestOnlineStudent findByTestOnlineAndStudentAndStatus(TestOnline testOnline, Student student, boolean status);
}
