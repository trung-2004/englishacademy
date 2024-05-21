package com.englishacademy.EnglishAcademy.models.booking;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateBooking {
    private Long tutorId;
    private Date bookingTime;
    private Integer duration;
    private String description;
}
