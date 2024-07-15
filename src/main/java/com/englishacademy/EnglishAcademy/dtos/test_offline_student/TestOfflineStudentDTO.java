package com.englishacademy.EnglishAcademy.dtos.test_offline_student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestOfflineStudentDTO {
    private Long id;
    private String code;
    private Double score;
    private Date time;
    private boolean status;
    private boolean isPassed;
    private Long studentId;
    private Long testOfflineId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
