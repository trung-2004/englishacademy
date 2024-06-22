package com.englishacademy.EnglishAcademy.models.item_slot;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CreateItemSlot {
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    @NotNull(message = "ItemType cannot be null")
    private Integer itemType;
    private Date startDate;
    private Date endDate;
    @NotNull(message = "OrderTop cannot be null")
    private Integer orderTop;
    @NotNull(message = "Class id cannot be null")
    private Long classId;
    @NotNull(message = "Slot id cannot be null")
    private Long slotId;
    private String pathUrl;
}
