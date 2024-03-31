package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.TestOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestOnlineRepository extends JpaRepository<TestOnline, Long> {
    List<TestOnline> findAllByType(Integer type);
    TestOnline findBySlug(String slug);
}
