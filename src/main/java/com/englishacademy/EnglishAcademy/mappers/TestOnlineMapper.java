package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineResponseDTO;
import com.englishacademy.EnglishAcademy.entities.TestOnline;
import org.springframework.stereotype.Component;

@Component
public class TestOnlineMapper {
    public TestOnlineDTO toTestOnlineDTO(TestOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        TestOnlineDTO testOnlineDTO = TestOnlineDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .type(model.getType())
                .pastMark(model.getPastMark())
                .time(model.getTime())
                .totalMark(model.getTotalMark())
                .description(model.getDescription())
                .totalQuestion(model.getTotalQuestion())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return testOnlineDTO;
    }

    public TestOnlineResponseDTO toTestOnlineResponseDTO(TestOnline model, boolean status){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        TestOnlineResponseDTO testOnlineResponseDTO = TestOnlineResponseDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .type(model.getType())
                .pastMark(model.getPastMark())
                .time(model.getTime())
                .totalMark(model.getTotalMark())
                .status(status)
                .description(model.getDescription())
                .totalQuestion(model.getTotalQuestion())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return testOnlineResponseDTO;
    }
}
