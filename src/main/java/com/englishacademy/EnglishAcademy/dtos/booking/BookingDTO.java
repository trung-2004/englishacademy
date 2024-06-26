package com.englishacademy.EnglishAcademy.dtos.booking;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private Long id;
    private Long tutorId;
    private Long studentId;
    private Long paymentId;
    private Date startTime;
    private Date endTime;
    private String tutorName;
    private String studentName;
    private String description;
    private BookingStatus status;
    private String lessonDays;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
