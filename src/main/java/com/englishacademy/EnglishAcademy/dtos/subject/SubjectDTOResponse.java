package com.englishacademy.EnglishAcademy.dtos.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTOResponse {
    private Long id;
    private String name;
    private String slug;
    private Integer orderTop;
    private Integer totalSlot;
    private Double score;
    private boolean status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
