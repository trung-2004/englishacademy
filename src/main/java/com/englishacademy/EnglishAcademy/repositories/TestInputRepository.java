package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.TestInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestInputRepository extends JpaRepository<TestInput, Long> {
    List<TestInput> findAllByType(Integer type);
    TestInput findBySlug(String slug);
}
