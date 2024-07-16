package com.englishacademy.EnglishAcademy.dtos.test_offline;

import com.englishacademy.EnglishAcademy.dtos.test_session.TestOfflineSessionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestOfflineDetail {
    private Long id;
    private String title;
    private String slug;
    private LocalDateTime startDate;
    private LocalDateTime endtDate;
    private Integer totalQuestion;
    private Integer pastMark;
    private Integer totalMark;
    private Integer retakeTestId;
    private List<TestOfflineSessionDetail> testOfflineSessionDetails;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
