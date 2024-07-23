package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CourseOnlineStudentRepository extends JpaRepository<CourseOnlineStudent, Long> {
    CourseOnlineStudent findByCourseOnlineAndStudent(CourseOnline courseOnline, Student student);
    @Query("SELECT SUM(b.totalPrice) FROM CourseOnlineStudent b")
    Double getTotalRevenue();

    @Query("SELECT new com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO(" +
            "FUNCTION('YEAR', cos.createdDate), " +
            "FUNCTION('MONTH', cos.createdDate), " +
            "SUM(cos.totalPrice)) " +
            "FROM CourseOnlineStudent cos " +
            "WHERE cos.createdDate >= :startDate " +
            "GROUP BY FUNCTION('YEAR', cos.createdDate), FUNCTION('MONTH', cos.createdDate) " +
            "ORDER BY FUNCTION('YEAR', cos.createdDate), FUNCTION('MONTH', cos.createdDate)")
    List<CourseOnlineMonthlyRevenueDTO> getMonthlyRevenueLast12Months(@Param("startDate") Timestamp startDate);

    @Query("SELECT new com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineRevenueDTO(" +
            "cos.courseOnline.id, cos.courseOnline.name, cos.courseOnline.slug, cos.courseOnline.image, cos.courseOnline.price, cos.courseOnline.star, cos.courseOnline.status, cos.courseOnline.trailer, cos.courseOnline.category.id, SUM(cos.totalPrice)) " +
            "FROM CourseOnlineStudent cos " +
            "GROUP BY cos.courseOnline.id, cos.courseOnline.name " +
            "ORDER BY SUM(cos.totalPrice) DESC")
    List<CourseOnlineRevenueDTO> findTop10CoursesByRevenue();
}
