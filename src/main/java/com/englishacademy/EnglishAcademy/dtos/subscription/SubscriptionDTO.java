package com.englishacademy.EnglishAcademy.dtos.subscription;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {
    private Long id;
    private Long tutor_Id;
    private Long student_Id;
    private Date startTime;
    private Date endTime;
    private Date nextPaymentDate ;
    private String lessonDays;
    private Double price;
    private BookingStatus status ;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
