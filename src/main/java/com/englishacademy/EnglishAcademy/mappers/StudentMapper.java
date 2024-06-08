package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDTO toStudentDTO(Student model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        StudentDTO studentDTO = StudentDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .fullName(model.getFullName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .status(model.getStatus())
                .gender(model.getGender())
                .dayOfBirth(model.getDayOfBirth())
                .startDate(model.getStartDate())
                .address(model.getAddress())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return studentDTO;
    }
}
