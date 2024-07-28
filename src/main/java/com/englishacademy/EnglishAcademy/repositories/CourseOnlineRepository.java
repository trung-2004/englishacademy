package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOnlineRepository extends JpaRepository<CourseOnline, Long> {
    CourseOnline findBySlug(String slug);
    List<CourseOnline> findAllByCategoryIdAndLevelIn(Long id, List<Integer> levels);
    @Query(value = "SELECT * FROM `course_online` ORDER BY star desc LIMIT 6", nativeQuery = true)
    List<CourseOnline> findAllCourseLimit();

    @Query(value = "SELECT * FROM `course_online` WHERE category_id = :categoryId AND id != :currentCourseId LIMIT 4 OFFSET 0", nativeQuery = true)
    List<CourseOnline> findRelatedCoursesByCategoryId(@Param("categoryId") Long categoryId, @Param("currentCourseId") Long currentCourseId);
}
