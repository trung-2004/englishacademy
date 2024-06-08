package com.englishacademy.EnglishAcademy.dtos.test_session;

import com.englishacademy.EnglishAcademy.dtos.question_test_offline.QuestionTestOfflineDTO;
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
public class TestOfflineSessionDetail {
    private Long id;
    private Long testOfflineId;
    private Long sessionId;
    private String sessionName;
    private Integer totalQuestion;
    private Integer orderTop;
    private List<QuestionTestOfflineDTO> questionTestOfflineDTOS;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
