package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorRevenueDTO;
import com.englishacademy.EnglishAcademy.entities.Payment;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import com.englishacademy.EnglishAcademy.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByStudentPackage(StudentPackage studentPackage);
    Payment findBySubscription(Subscription subscription);
    @Query("SELECT SUM(p.price) FROM Payment p where p.isPaid = true ")
    Double getTotalRevenue();

    @Query("SELECT new com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO(" +
            "FUNCTION('YEAR', cos.createdDate), " +
            "FUNCTION('MONTH', cos.createdDate), " +
            "SUM(cos.price)) " +
            "FROM Payment cos " +
            "WHERE cos.createdDate >= :startDate " +
            "GROUP BY FUNCTION('YEAR', cos.createdDate), FUNCTION('MONTH', cos.createdDate) " +
            "ORDER BY FUNCTION('YEAR', cos.createdDate), FUNCTION('MONTH', cos.createdDate)")
    List<CourseOnlineMonthlyRevenueDTO> getMonthlyRevenueLast12Months(@Param("startDate") Timestamp startDate);

    @Query("SELECT new com.englishacademy.EnglishAcademy.dtos.tutor.TutorRevenueDTO(" +
            "t.id, t.user.fullName, t.code, t.level, t.avatar, t.hourlyRate, SUM(p.price)) " +
            "FROM Booking b " +
            "JOIN b.tutor t " +
            "JOIN b.payment p " +
            "WHERE p.isPaid = true " +
            "GROUP BY t.id, t.code " +
            "ORDER BY SUM(p.price) DESC")
    List<TutorRevenueDTO> findTop10CoursesByRevenue();
}
