package com.englishacademy.EnglishAcademy.models.test_input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditTestInput {
    @NotNull(message = "Id is mandatory")
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "Type is mandatory")
    private Integer type;
    @NotNull(message = "Time is mandatory")
    private Integer time;
    @NotBlank(message = "Des is mandatory")
    private String description;
    @NotNull(message = "Total Question is manadatory")
    private Integer totalQuestion;
}
