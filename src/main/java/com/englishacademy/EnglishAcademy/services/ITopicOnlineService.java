package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;

import java.util.List;

public interface ITopicOnlineService {
    List<TopicOnlineDetail> findAllByCourseSlug(String slug, Long userId);
}
