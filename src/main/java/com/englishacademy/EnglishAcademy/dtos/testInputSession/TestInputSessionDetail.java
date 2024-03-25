package com.englishacademy.EnglishAcademy.dtos.testInputSession;

import com.englishacademy.EnglishAcademy.dtos.questionTestInput.QuestionTestInputDTO;
import com.englishacademy.EnglishAcademy.entities.QuestionTestInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestInputSessionDetail {
    private Long testInputId;

    private Long sessionId;

    private String sessionName;

    private Integer totalQuestion;

    private Integer orderTop;

    private List<QuestionTestInputDTO> questionTestInputs;
}
