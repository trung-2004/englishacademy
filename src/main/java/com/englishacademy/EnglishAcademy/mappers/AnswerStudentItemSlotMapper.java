package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.answerStudentItemSlot.AnswerStudentItemSlotResponse;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import org.springframework.stereotype.Component;

@Component
public class AnswerStudentItemSlotMapper {
    public AnswerStudentItemSlotResponse toAnswerStudentItemSlotResponse(AnswerStudentItemSlot model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        AnswerStudentItemSlotResponse answerStudentItemSlotResponse = AnswerStudentItemSlotResponse.builder()
                .id(model.getId())
                .time(model.getTime())
                .content(model.getContent())
                .star(model.getStar())
                .star1Count(model.getStar1Count())
                .star2Count(model.getStar2Count())
                .star3Count(model.getStar3Count())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return answerStudentItemSlotResponse;
    }
}
