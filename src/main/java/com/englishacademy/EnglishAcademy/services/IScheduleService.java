package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.schedule.ScheduleStudent;

import java.util.List;

public interface IScheduleService {
    List<ScheduleStudent> getScheduleStudent(Long id);
}
