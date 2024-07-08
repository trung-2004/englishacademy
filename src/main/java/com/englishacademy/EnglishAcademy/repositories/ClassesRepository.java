package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Classes;
import com.englishacademy.EnglishAcademy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
    List<Classes> findAllByTeacherAndStatus(User user, boolean bool);
    @Query(value = "SELECT COUNT(*) FROM Class c WHERE c.teacher_id = :teacherId and c.status = true", nativeQuery = true)
    Integer findAllByTeacherCount(@Param("teacherId") Long teacherId);
}
