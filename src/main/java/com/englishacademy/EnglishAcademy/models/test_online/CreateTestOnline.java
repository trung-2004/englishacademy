package com.englishacademy.EnglishAcademy.models.test_online;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateTestOnline {
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
    @NotNull(message = "Topic is mandatory")
    private Long topicId;
    @NotBlank(message = "file is mandatory")
    private MultipartFile file;
}
