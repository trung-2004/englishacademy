package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Booking;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByStudent(Student student);
    List<Booking> findAllByTutor(Tutor tutor);
    @Query(value = "SELECT COUNT(DISTINCT b.student_id) FROM Booking b WHERE b.tutor_id = :tutorId AND b.status = 2",nativeQuery = true)
    int getCountStudentStudying(@Param("tutorId") Long tutorId);
    //@Query(value = "SELECT DISTINCT s FROM Booking b JOIN Student s ON b.student_id = s.id WHERE b.tutor_id = :tutorId AND b.status = 2", nativeQuery = true)
    @Query("SELECT DISTINCT b.student FROM Booking b WHERE b.tutor.id = :tutorId AND b.status = 2")
    List<Student> findActiveStudentsByTutorId(@Param("tutorId") Long tutorId);
}
