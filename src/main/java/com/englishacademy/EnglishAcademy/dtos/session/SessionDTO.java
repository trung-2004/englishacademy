package com.englishacademy.EnglishAcademy.dtos.session;

import com.englishacademy.EnglishAcademy.dtos.question_test_input.QuestionTestInputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO {
    private String title;

    private Integer type;

    private Integer orderTop;

    private String totalQuestion;

    private List<QuestionTestInputDTO> questionTestInputDTOS;
}
