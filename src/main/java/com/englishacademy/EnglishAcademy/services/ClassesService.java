package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.classes.CLassesDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.classes.CreateClasses;

import java.util.List;

public interface ClassesService {
    List<CLassesDTO> getAllByTeacher(User currentUser);
    CLassesDTO create(CreateClasses createClasses);
    int countClassesByTeacher(User currentUser);
}
