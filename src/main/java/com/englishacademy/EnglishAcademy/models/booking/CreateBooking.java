package com.englishacademy.EnglishAcademy.models.booking;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateBooking {
    private Integer typeBooking;
    private Long tutorId;
    private Long packageId;
    private String lessonDays;
    private String description;
}
