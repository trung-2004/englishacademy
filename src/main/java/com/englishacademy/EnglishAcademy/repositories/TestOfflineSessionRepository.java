package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOfflineSessionRepository extends JpaRepository<TestOfflineSession, Long> {
    TestOfflineSession findByTestOfflineAndSession(TestOffline testOffline, Session session);
}
