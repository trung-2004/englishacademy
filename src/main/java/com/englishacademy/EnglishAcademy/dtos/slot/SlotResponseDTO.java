package com.englishacademy.EnglishAcademy.dtos.slot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotResponseDTO {
    private Long id;
    private String name;

    private String slug;

    private Integer orderTop;
    private String time;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
