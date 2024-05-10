package com.englishacademy.EnglishAcademy.models.answerStudent;

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
