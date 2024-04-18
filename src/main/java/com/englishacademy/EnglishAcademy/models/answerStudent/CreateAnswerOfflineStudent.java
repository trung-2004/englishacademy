package com.englishacademy.EnglishAcademy.models.answerStudent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateAnswerOfflineStudent {
    private String content;
    private Long questionId;
    private MultipartFile file;
}
