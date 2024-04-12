package com.englishacademy.EnglishAcademy.dtos.itemSlot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemSlotResponse {
    private Long id;
    private String title;
    private String slug;
    private Integer orderTop;
    private Integer itemType;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
