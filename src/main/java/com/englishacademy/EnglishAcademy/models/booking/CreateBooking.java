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
    private List<LessonDay> lessonDays;
    private String description;
    public static class LessonDay {
        private String dayOfWeek;
        private String startTime;
        private String endTime;

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public LessonDay setDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public String getStartTime() {
            return startTime;
        }

        public LessonDay setStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public String getEndTime() {
            return endTime;
        }

        public LessonDay setEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }
        // Getters and Setters
    }
}
