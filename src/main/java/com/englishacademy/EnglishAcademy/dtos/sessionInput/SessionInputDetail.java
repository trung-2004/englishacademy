package com.englishacademy.EnglishAcademy.dtos.sessionInput;

import com.englishacademy.EnglishAcademy.dtos.questionTestInput.QuestionTestInputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionInputDetail {
    private String title;

    private Integer type;

    private Integer orderTop;

    private String totalQuestion;

    private List<QuestionTestInputDTO> questionTestInputDTOS;
}
