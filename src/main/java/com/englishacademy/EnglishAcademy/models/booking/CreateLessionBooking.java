package com.englishacademy.EnglishAcademy.models.booking;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CreateLessionBooking {
    private Long bookingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
