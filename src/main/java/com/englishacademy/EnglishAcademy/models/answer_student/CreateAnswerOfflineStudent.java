package com.englishacademy.EnglishAcademy.models.answer_student;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswerOfflineStudent {
    private String content;
    private Long questionId;
}
