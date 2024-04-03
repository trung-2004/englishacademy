package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Classes;
import com.englishacademy.EnglishAcademy.entities.CourseOffline;
import com.englishacademy.EnglishAcademy.entities.CourseOfflineStudent;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfflineStudentRepository extends JpaRepository<CourseOfflineStudent, Long> {
    List<CourseOfflineStudent> findAllByClasses(Classes classes);
    CourseOfflineStudent findByClassesAndCourseOffline(Classes classes, CourseOffline courseOffline);
}
