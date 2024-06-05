package com.englishacademy.EnglishAcademy.dtos.packages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageDTO {
    private String name;
    private String description;
    private Integer numSessions;
    private Double hourlyRate;
    private Long id;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
