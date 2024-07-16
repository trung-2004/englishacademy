package com.englishacademy.EnglishAcademy.dtos.test_offline_student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestOfflineStudentDTO {
    private Long id;
    private String code;
    private Double score;
    private LocalDateTime time;
    private boolean status;
    private boolean isPassed;
    private Long studentId;
    private Long testOfflineId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
