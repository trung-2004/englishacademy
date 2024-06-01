package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import com.englishacademy.EnglishAcademy.entities.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    public SubjectDTO toSubjectDTO(Subject model){
        if (model == null) {
            throw new RuntimeException("    Not Found");
        }
        SubjectDTO subjectDTO = SubjectDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .totalSlot(model.getTotalSlot())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return subjectDTO;
    }
}
