package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.topic_online.CourseOnlineTopicDetailResponse;

public interface TopicOnlineService {
    CourseOnlineTopicDetailResponse findAllByCourseSlug(String slug, Long userId);
}
