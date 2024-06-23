package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.classes.CLassesDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.entities.User;

import java.util.List;

public interface ClassesService {
    List<CLassesDTO> getAllByTeacher(User currentUser);
}
