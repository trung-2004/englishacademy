package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.topicOnline.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetailResponse;

import java.util.List;

public interface ITopicOnlineService {
    CourseOnlineTopicDetailResponse findAllByCourseSlug(String slug, Long userId);
}
