package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.topic_online.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDTO;
import com.englishacademy.EnglishAcademy.models.topic_online.CreateTopicOnline;
import com.englishacademy.EnglishAcademy.models.topic_online.EditTopicOnline;

public interface TopicOnlineService {
    CourseOnlineTopicDetailResponse findAllByCourseSlug(String slug, Long userId);

    TopicOnlineDTO findBySlug(String slug);

    TopicOnlineDTO create(CreateTopicOnline createTopicOnline);

    TopicOnlineDTO edit(EditTopicOnline editTopicOnline);

    void delete(Long[] ids);
}
