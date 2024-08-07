package com.englishacademy.EnglishAcademy.models.test_input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
public class CreateTestInput {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "Type is mandatory")
    private Integer type;
    @NotNull(message = "Time is mandatory")
    private Integer time;
    @NotBlank(message = "Des is mandatory")
    private String description;
    @NotBlank(message = "File is mandatory")
    private MultipartFile file;
}
