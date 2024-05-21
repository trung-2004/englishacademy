package com.englishacademy.EnglishAcademy.models.booking;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateLessionBooking {
    private Long bookingId;
    private Date bookingTime;
    private Integer duration;
}
