package com.englishacademy.EnglishAcademy.models.booking;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateBooking {
    private Integer typeBooking;
    private Long tutorId;
    private Long packageId;
    private List<Long> lessonDays;
    private String description;
}
