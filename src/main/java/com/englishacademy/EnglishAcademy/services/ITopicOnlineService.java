package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetailResponse;

import java.util.List;

public interface ITopicOnlineService {
    List<TopicOnlineDetailResponse> findAllByCourseSlug(String slug, Long userId);
}
