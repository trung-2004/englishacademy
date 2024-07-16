package com.englishacademy.EnglishAcademy.dtos.booking;

import com.englishacademy.EnglishAcademy.dtos.lession_booking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private Long tutorId;
    private Long studentId;
    private Long paymentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tutorName;
    private String studentName;
    private String description;
    private BookingStatus status;
    private List<LessonDay> lessonDays;
    private List<LessionBookingDTO> lessionBookingDTOS;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
