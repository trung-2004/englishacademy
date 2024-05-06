package com.englishacademy.EnglishAcademy.dtos.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleStudent {
    private Long id;
    private Long classId;
    private String className;
    private Long roomId;
    private String roomName;
    private Date startTime;
    private Date endTime;
    private String dayOfWeek;
    private String ca;
}
