package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOfflineStudentRepository extends JpaRepository<TestOfflineStudent, Long> {
    TestOfflineStudent findByCode(String code);
    TestOfflineStudent findByTestOfflineAndStudent(TestOffline testOffline, Student student);
    TestOfflineStudent findByTestOfflineAndStudentAndStatus(TestOffline testOffline, Student student, boolean status);
}
