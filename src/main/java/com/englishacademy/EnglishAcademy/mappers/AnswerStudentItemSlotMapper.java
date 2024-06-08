package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot.AnswerStudentItemSlotResponse;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class AnswerStudentItemSlotMapper {
    public AnswerStudentItemSlotResponse toAnswerStudentItemSlotResponse(AnswerStudentItemSlot model){
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        AnswerStudentItemSlotResponse answerStudentItemSlotResponse = AnswerStudentItemSlotResponse.builder()
                .id(model.getId())
                .time(model.getTime())
                .content(model.getContent())
                .star(model.getStar())
                .star1Count(model.getStar1Count())
                .star2Count(model.getStar2Count())
                .star3Count(model.getStar3Count())
                .studentId(model.getStudent().getId())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return answerStudentItemSlotResponse;
    }
}
