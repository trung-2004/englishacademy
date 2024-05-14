package com.englishacademy.EnglishAcademy.models.testInput;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
public class CreateTestInput {
    private String title;
    private Integer type;
    private Integer time;
    private String description;
    private MultipartFile file;
}
