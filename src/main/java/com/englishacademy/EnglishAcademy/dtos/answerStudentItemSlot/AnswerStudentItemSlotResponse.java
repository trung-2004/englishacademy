package com.englishacademy.EnglishAcademy.dtos.answerStudentItemSlot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerStudentItemSlotResponse {
    private Long id;
    private String content;
    private Integer star;
    private Date time;
    private Integer star3Count;
    private Integer star2Count;
    private Integer star1Count;
    private Long studentId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
