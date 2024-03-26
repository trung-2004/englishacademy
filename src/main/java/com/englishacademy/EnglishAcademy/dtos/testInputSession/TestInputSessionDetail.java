package com.englishacademy.EnglishAcademy.dtos.testInputSession;

import com.englishacademy.EnglishAcademy.dtos.questionTestInput.QuestionTestInputDTO;
import com.englishacademy.EnglishAcademy.entities.QuestionTestInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestInputSessionDetail {
    private Long id;
    private Long testInputId;

    private Long sessionId;

    private String sessionName;

    private Integer totalQuestion;

    private Integer orderTop;

    private List<QuestionTestInputDTO> questionTestInputs;
    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
