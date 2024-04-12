package com.englishacademy.EnglishAcademy.dtos.itemSlot;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemSlotDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private Integer itemType;
    private Integer orderTop;
    private String pathUrl;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
