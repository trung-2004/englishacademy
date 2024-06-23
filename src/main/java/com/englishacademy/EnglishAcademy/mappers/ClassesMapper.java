package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.classes.CLassesDTO;
import com.englishacademy.EnglishAcademy.entities.Classes;
import org.springframework.stereotype.Component;

@Component
public class ClassesMapper {
    public CLassesDTO toClassesDTO(Classes classes){
        if (classes == null) return null;
        CLassesDTO cLassesDTO = CLassesDTO.builder()
                .id(classes.getId())
                .name(classes.getName())
                .roomName(classes.getRoom().getName())
                .createdBy(classes.getCreatedBy())
                .modifiedBy(classes.getModifiedBy())
                .createdDate(classes.getCreatedDate())
                .modifiedDate(classes.getModifiedDate())
                .build();
        return cLassesDTO;
    }
}
