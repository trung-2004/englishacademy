package com.englishacademy.EnglishAcademy.dtos.booking;

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
    private Date bookingTime;
    private String tutorName;
    private String studentName;
    private Integer duration;
    private String description;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
