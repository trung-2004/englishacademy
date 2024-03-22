package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOnlineRepository extends JpaRepository<ItemOnline, Long> {
    ItemOnline findBySlug(String slug);
    List<ItemOnline> findAllByTopicOnline(TopicOnline topicOnline);
}
