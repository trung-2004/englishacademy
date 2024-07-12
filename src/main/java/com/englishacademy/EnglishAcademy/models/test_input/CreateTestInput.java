package com.englishacademy.EnglishAcademy.models.test_input;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Type is mandatory")
    private Integer type;
    @NotBlank(message = "Name is mandatory")
    private Integer time;
    @NotBlank(message = "Name is mandatory")
    private String description;
    @NotBlank(message = "Name is mandatory")
    private MultipartFile file;
}
