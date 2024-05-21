package com.englishacademy.EnglishAcademy.models.availabilitiy;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class CreateAvailability {
    private Long tutorId;
    private LocalTime startTime;
    private LocalTime endTime;
}
