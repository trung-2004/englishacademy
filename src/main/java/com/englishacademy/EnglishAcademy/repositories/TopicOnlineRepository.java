package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicOnlineRepository extends JpaRepository<TopicOnline, Long> {
    List<TopicOnline> findAllByCourseOnline(CourseOnline courseOnline);
    TopicOnline findBySlug(String slug);
    @Query(value = "SELECT t.id FROM Topic t WHERE t.course.id = :courseId", nativeQuery = true)
    List<Long> findTopicIdsByCourseId(@Param("courseId") Long courseId);
}
