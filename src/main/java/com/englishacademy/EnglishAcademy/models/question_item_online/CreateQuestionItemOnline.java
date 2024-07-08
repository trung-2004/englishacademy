package com.englishacademy.EnglishAcademy.models.question_item_online;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreateQuestionItemOnline {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Answer1 is mandatory")
    private String answer1;
    @NotBlank(message = "Answer2 is mandatory")
    private String answer2;
    @NotBlank(message = "Answer3 is mandatory")
    private String answer3;
    @NotBlank(message = "Answer4 is mandatory")
    private String answer4;
    @NotBlank(message = "Answer Correct is mandatory")
    private String answerCorrect;
    @NotNull(message = "Order Top is mandatory")
    private Integer orderTop;
    @NotNull(message = "Item Online Id is mandatory")
    private Long itemOnlineId;
}
