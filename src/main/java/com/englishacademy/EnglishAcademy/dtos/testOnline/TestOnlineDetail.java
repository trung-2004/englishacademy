package com.englishacademy.EnglishAcademy.dtos.testOnline;

import com.englishacademy.EnglishAcademy.dtos.testInputSession.TestInputSessionDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnlineSession.TestOnlineSessionDetail;
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
public class TestOnlineDetail {
    private Long id;
    private String title;
    private String slug;
    private Integer type;
    private String description;
    private Integer time;
    private Integer totalQuestion;
    private List<TestOnlineSessionDetail> testOnlineSessionDetails;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
