package com.englishacademy.EnglishAcademy.models.item_online;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateItemOnline {
    @NotBlank(message = "Path URL is mandatory")
    private String pathUrl;
    @NotNull(message = "Order is mandatory")
    private Integer orderTop;
    @NotNull(message = "Duration is mandatory")
    private Integer duration;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Content is mandatory")
    private String content;
    @NotNull(message = "Item Type is mandatory")
    @Min(value = 0, message = "Item Type must be at least 0")
    @Max(value = 3, message = "Item Type must be at most 3")
    private Integer itemType;
    @NotNull(message = "Topic Online ID is mandatory")
    @Positive(message = "Topic Online ID must be a positive number")
    private Long topicOnlineId;
}
