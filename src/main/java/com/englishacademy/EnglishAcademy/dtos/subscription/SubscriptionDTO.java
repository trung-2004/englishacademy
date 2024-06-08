package com.englishacademy.EnglishAcademy.dtos.subscription;

import com.englishacademy.EnglishAcademy.dtos.booking.LessonDay;
import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {
    private Long id;
    private Long tutorId;
    private Long studentId;
    private String studentName;
    private Date startTime;
    private Date endTime;
    private Date nextPaymentDate ;
    private List<LessonDay> lessonDays;
    private Double price;
    private BookingStatus status ;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
