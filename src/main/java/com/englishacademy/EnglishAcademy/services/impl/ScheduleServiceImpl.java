package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.schedule.ScheduleStudent;
import com.englishacademy.EnglishAcademy.entities.Schedule;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.ScheduleRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, StudentRepository studentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<ScheduleStudent> getScheduleStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        if (student.getClasses() == null) throw new AppException(ErrorCode.CLASS_NOTFOUND);

        List<Schedule> scheduleList = scheduleRepository.findAllByClasses(student.getClasses());
        List<ScheduleStudent> scheduleStudentList = scheduleList.stream().map(this::scheduleToStudent).collect(Collectors.toList());
        return scheduleStudentList;
    }

    private ScheduleStudent scheduleToStudent(Schedule schedule){
        if (schedule == null) throw new AppException(ErrorCode.NOTFOUND);
        ScheduleStudent scheduleStudent = ScheduleStudent.builder()
                .id(schedule.getId())
                .classId(schedule.getClasses().getId())
                .className(schedule.getClasses().getName())
                .roomId(schedule.getRoom().getId())
                .roomName(schedule.getRoom().getName())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .dayOfWeek(schedule.getDayOfWeek())
                .ca(schedule.getCa())
                .build();
        return scheduleStudent;
    }
}
