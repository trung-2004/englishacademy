package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.availability.AvailabilityDTO;
import com.englishacademy.EnglishAcademy.entities.Availability;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityMapper {
    public AvailabilityDTO availabilityToAvailabilityDTO(Availability model) {
        if (model == null){
            throw new AppException(ErrorCode.NOTFOUND);
        }
        AvailabilityDTO availabilityDTO = AvailabilityDTO.builder()
                .id(model.getId())
                .tutorId(model.getTutor().getId())
                .tutorName(model.getTutor().getUser().getFullName())
                .startTime(model.getStartTime())
                .endTime(model.getEndTime())
                .status(model.isStatus())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
        return availabilityDTO;
    }
}
