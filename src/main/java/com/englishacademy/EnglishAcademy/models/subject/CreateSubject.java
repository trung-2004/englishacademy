package com.englishacademy.EnglishAcademy.models.subject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSubject {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "OrderTop cannot be null")
    private Integer orderTop;
    @NotNull(message = "TotalSlot cannot be null")
    private Integer totalSlot;
    @NotNull(message = "Course Offline Id cannot be null")
    private Long courseOfflineId;
}
