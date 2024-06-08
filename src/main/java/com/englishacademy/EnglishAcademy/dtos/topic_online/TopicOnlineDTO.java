package com.englishacademy.EnglishAcademy.dtos.topic_online;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicOnlineDTO {
    private Long id;
    private String name;
    private String slug;
    private Integer orderTop;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
