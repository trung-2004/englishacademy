package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOnlineSessionRepository extends JpaRepository<TestOnlineSession, Long> {
    TestOnlineSession findByTestOnlineAndSession(TestOnline testOnline, Session session);
}
