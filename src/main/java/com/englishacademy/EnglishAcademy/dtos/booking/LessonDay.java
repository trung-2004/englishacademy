package com.englishacademy.EnglishAcademy.dtos.booking;

import com.englishacademy.EnglishAcademy.entities.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDay {
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
}
