package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.studentPackage.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class StudentPackageMapper {
    public StudentPackageDTO toStudentPackageDTO(StudentPackage model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        StudentPackageDTO studentPackageDTO = StudentPackageDTO.builder()
                .id(model.getId())
                .package_Id(model.getPackages().getId())
                .student_Id(model.getStudent().getId())
                .lessonDays(model.getLessonDays())
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
