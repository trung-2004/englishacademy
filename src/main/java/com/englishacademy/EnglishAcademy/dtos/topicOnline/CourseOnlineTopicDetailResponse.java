package com.englishacademy.EnglishAcademy.dtos.topicOnline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOnlineTopicDetailResponse {
    private String name;
    private String slug;
    private boolean status;
    private Integer progress;
    private List<TopicOnlineDetailResponse> topicOnlineDetailResponseList;
}
