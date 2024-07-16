package com.englishacademy.EnglishAcademy.models.test_offline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class CreateTestOffline {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "PastMark is mandatory")
    private Integer pastMark;
    @NotNull(message = "TotalMark is mandatory")
    private Integer totalMark;
    @NotNull(message = "Start Date is mandatory")
    private LocalDateTime startDate;
    @NotNull(message = "End Date is mandatory")
    private LocalDateTime endDate;
    @NotNull(message = "Topic is mandatory")
    private Long subjectId;
    @NotBlank(message = "file is mandatory")
    private MultipartFile file;
}
