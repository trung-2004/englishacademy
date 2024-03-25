package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDTO;
import com.englishacademy.EnglishAcademy.entities.TestInput;
import org.springframework.stereotype.Component;

@Component
public class TestInputMapper {
    public TestInputDTO toTestInputDTO(TestInput model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        TestInputDTO testInputDTO = TestInputDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .type(model.getType())
                .description(model.getDescription())
                .totalQuestion(model.getTotalQuestion())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return testInputDTO;
    }
}
