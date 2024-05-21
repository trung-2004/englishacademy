package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDetail;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class TutorMapper {
    public TutorDTO toTutorDTO(Tutor model){
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        TutorDTO tutorDTO = TutorDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .fullname(model.getUser().getFullName())
                .avatar(model.getAvatar())
                .address(model.getAddress())
                .level(model.getLevel())
                .hourlyRate(model.getHourlyRate())
                .teachingSubject(model.getTeachingSubject())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return tutorDTO;
    }

    public TutorDetail toTutorDetail(Tutor model){
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        TutorDetail tutorDTO = TutorDetail.builder()
                .id(model.getId())
                .fullname(model.getUser().getFullName())
                .avatar(model.getAvatar())
                .address(model.getAddress())
                .level(model.getLevel())
                .cetificate(model.getCetificate())
                .experience(model.getExperience())
                .phone(model.getUser().getPhone())
                .hourlyRate(model.getHourlyRate())
                .teachingSubject(model.getTeachingSubject())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return tutorDTO;
    }
}
