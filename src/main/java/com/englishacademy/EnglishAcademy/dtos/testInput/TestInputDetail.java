package com.englishacademy.EnglishAcademy.dtos.testInput;

import com.englishacademy.EnglishAcademy.dtos.sessionInput.SessionInputDetail;
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
public class TestInputDetail {
    private Long id;
    private String title;
    private String slug;
    private Integer type;
    private String description;
    private String totalQuestion;
    private List<SessionInputDetail> sessionInputDetails;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
