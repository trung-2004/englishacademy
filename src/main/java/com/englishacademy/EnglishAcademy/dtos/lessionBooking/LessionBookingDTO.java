package com.englishacademy.EnglishAcademy.dtos.lessionBooking;

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
public class LessionBookingDTO {
    private Long bookingId;
    private Date bookingTime;
    private Integer duration;
    private Integer status;
    private boolean isPaid;
    private Double price;
    private String paymentMethod;
    private Long id;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
