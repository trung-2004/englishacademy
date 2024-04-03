package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.CourseOffline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfflineRepository extends JpaRepository<CourseOffline, Long> {
    CourseOffline findBySlug(String slug);
    List<CourseOffline> findAllByCourseOfflineStudents(CourseOnlineStudent courseOnlineStudent);
}
