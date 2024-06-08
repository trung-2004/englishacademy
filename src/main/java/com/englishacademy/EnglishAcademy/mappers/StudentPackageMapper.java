package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.student_package.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.utils.JsonConverterUtil;
import org.springframework.stereotype.Component;

@Component
public class StudentPackageMapper {
    public StudentPackageDTO toStudentPackageDTO(StudentPackage model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        StudentPackageDTO studentPackageDTO = StudentPackageDTO.builder()
                .id(model.getId())
                .packageId(model.getPackages().getId())
                .packageName(model.getPackages().getName())
                .studentId(model.getStudent().getId())
                .studentName(model.getStudent().getFullName())
                .lessonDays(JsonConverterUtil.convertJsonToLessonDay(model.getLessonDays()))
                .status(model.getStatus())
                .purchaseDate(model.getPurchaseDate())
                .remainingSessions(model.getRemainingSessions())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return studentPackageDTO;
    }
}
