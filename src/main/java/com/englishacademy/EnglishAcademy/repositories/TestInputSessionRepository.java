package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Session;
import com.englishacademy.EnglishAcademy.entities.TestInput;
import com.englishacademy.EnglishAcademy.entities.TestInputSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestInputSessionRepository extends JpaRepository<TestInputSession, Long> {
    TestInputSession findByTestInputAndSession(TestInput testInput, Session session);
}
