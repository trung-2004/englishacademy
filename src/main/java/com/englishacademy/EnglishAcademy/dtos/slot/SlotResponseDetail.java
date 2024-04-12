package com.englishacademy.EnglishAcademy.dtos.slot;

import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDTO;
import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotResponse;
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
public class SlotResponseDetail {
    private Long id;
    private String name;

    private String slug;

    private Integer orderTop;
    private String time;
    private List<ItemSlotResponse> itemSlotResponseList;
    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
