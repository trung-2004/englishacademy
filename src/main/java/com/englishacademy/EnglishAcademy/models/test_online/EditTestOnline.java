package com.englishacademy.EnglishAcademy.models.test_online;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditTestOnline {
    @NotNull(message = "Id is mandatory")
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "PastMark is mandatory")
    private Integer pastMark;
    @NotNull(message = "TotalMark is mandatory")
    private Integer totalMark;
    @NotNull(message = "Time is mandatory")
    private Integer time;
    @NotBlank(message = "description is mandatory")
    private String description;
}
