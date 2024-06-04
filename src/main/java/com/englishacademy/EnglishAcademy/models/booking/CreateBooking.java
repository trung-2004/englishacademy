package com.englishacademy.EnglishAcademy.models.booking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
public class CreateBooking {
    private Integer typeBooking;
    private Long tutorId;
    private Long packageId;
    private List<LessonDay> lessonDays;
    private String description;
    public static class LessonDay {
        private Integer dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;

        public Integer getDayOfWeek() {
            return dayOfWeek;
        }

        public LessonDay setDayOfWeek(Integer dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LessonDay setStartTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public LessonDay setEndTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        // Getters and Setters
    }
}
