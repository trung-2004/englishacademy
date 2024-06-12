package com.englishacademy.EnglishAcademy.models.topic_online;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditTopicOnline {
    @NotNull(message = "ID is mandatory")
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Order is mandatory")
    private Integer orderTop;
    @NotNull(message = "Course Online ID is mandatory")
    @Positive(message = "Course Online ID must be a positive number")
    private Long courseOnlineId;
}
