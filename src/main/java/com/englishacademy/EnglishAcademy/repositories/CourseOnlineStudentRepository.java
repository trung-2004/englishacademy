package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseOnlineStudentRepository extends JpaRepository<CourseOnlineStudent, Long> {
    CourseOnlineStudent findByCourseOnlineAndStudent(CourseOnline courseOnline, Student student);
}
