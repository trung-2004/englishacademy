package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicOnlineRepository extends JpaRepository<TopicOnline, Long> {
    List<TopicOnline> findAllByCourseOnline(CourseOnline courseOnline);
    TopicOnline findBySlug(String slug);
}
