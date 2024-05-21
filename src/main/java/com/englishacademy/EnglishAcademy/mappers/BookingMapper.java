package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.entities.Booking;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingDTO toBookingDTO(Booking model){
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        BookingDTO bookingDTO = BookingDTO.builder()
                .id(model.getId())
                .tutorId(model.getTutor().getId())
                .studentId(model.getStudent().getId())
                .studentName(model.getStudent().getFullName())
                .tutorName(model.getTutor().getUser().getFullName())
                .bookingTime(model.getBookingTime())
                .description(model.getDescription())
                .duration(model.getDuration())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return bookingDTO;
    }
}
