package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.CourseOffline;
import com.englishacademy.EnglishAcademy.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findBySlug(String slug);
    List<Subject> findAllByCourseOffline(CourseOffline courseOffline);
}
