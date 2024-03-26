package com.englishacademy.EnglishAcademy.dtos.testInputStudent;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestInputStudentDTO {
    private Long id;
    private String code;
    private Integer correctReading;
    private Integer correctListening;
    private Integer correctVocabulary;
    private Integer correctGrammar;
    private Integer totalQuestionReading;
    private Integer totalQuestionListening;
    private Integer totalQuestionVocabulary;
    private Integer totalQuestionGrammar;
    private Double score;
    private Double time;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
