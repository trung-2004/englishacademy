package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.TestOffline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOfflineRepository extends JpaRepository<TestOffline, Long> {
    TestOffline findBySlug(String slug);
}
