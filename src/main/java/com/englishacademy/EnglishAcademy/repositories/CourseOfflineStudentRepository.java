package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO;
import com.englishacademy.EnglishAcademy.entities.Classes;
import com.englishacademy.EnglishAcademy.entities.CourseOffline;
import com.englishacademy.EnglishAcademy.entities.CourseOfflineStudent;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseOfflineStudentRepository extends JpaRepository<CourseOfflineStudent, Long> {
    List<CourseOfflineStudent> findAllByClasses(Classes classes);
    CourseOfflineStudent findByClassesAndCourseOffline(Classes classes, CourseOffline courseOffline);
    @Query("SELECT YEAR(cos.startDate) as year, MONTH(cos.startDate) as month, SUM(co.price) as revenue " +
            "FROM CourseOfflineStudent cos " +
            "JOIN cos.courseOffline co " +
            "WHERE cos.startDate >= :startDate " +
            "GROUP BY YEAR(cos.startDate), MONTH(cos.startDate) " +
            "ORDER BY YEAR(cos.startDate), MONTH(cos.startDate)")
    List<Object[]> getMonthlyRevenueRaw(@Param("startDate") LocalDateTime startDate);
}
