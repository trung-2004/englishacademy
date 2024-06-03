package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.lessionBooking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.LessionBooking;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class LessionBookingMapper {
    public LessionBookingDTO toLessionBookingDTO(LessionBooking model){
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        LessionBookingDTO lessionBookingDTO = LessionBookingDTO.builder()
                .id(model.getId())
                .bookingId(model.getBooking().getId())
                .actualStartTime(model.getActualStartTime())
                .actualEndTime(model.getActualEndTime())
                .scheduledStartTime(model.getScheduledStartTime())
                .scheduledEndTime(model.getScheduledEndTime())
                .status(model.getStatus())
                .path(model.getPath())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return lessionBookingDTO;
    }
}
