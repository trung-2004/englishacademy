package com.englishacademy.EnglishAcademy.models.answer_student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreAnswerStudentItemSlot {
    @NotNull(message = "Id is mandatory")
    private Long answerStudentItemSlotId;
    @NotNull(message = "Star is mandatory")
    @Min(value = 0, message = "Level must be at least 0")
    @Max(value = 10, message = "Level must be at most 10")
    private Integer star;
}
