package com.englishacademy.EnglishAcademy.dtos.test_online;

import com.englishacademy.EnglishAcademy.dtos.test_session.TestOnlineSessionDetail;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOnlineSessionDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestOnlineDetailResponse {
    private Long id;
    private String title;
    private String slug;
    private Integer type;
    private String description;
    private Integer time;
    private Integer totalQuestion;
    private List<TestOnlineSessionDetailResponse> testOnlineSessionDetails;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
