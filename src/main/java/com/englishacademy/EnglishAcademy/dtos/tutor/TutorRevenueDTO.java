package com.englishacademy.EnglishAcademy.dtos.tutor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorRevenueDTO {
    private Long id;
    private String fullname;
    private String code;
    private Integer level;
    private String avatar;
    private Double hourlyRate;
    private Double totalRevenue;
}
