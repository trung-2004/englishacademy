package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.classes.CLassesDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.entities.Classes;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.mappers.ClassesMapper;
import com.englishacademy.EnglishAcademy.models.classes.CreateClasses;
import com.englishacademy.EnglishAcademy.repositories.ClassesRepository;
import com.englishacademy.EnglishAcademy.services.ClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {
    private final ClassesRepository classesRepository;
    private final ClassesMapper classesMapper;

    @Override
    public List<CLassesDTO> getAllByTeacher(User currentUser) {
        return classesRepository.findAllByTeacherAndStatus(currentUser, true).stream().map(classesMapper::toClassesDTO).collect(Collectors.toList());
    }

    @Override
    public CLassesDTO create(CreateClasses createClasses) {
        return null;
    }

    @Override
    public int countClassesByTeacher(User currentUser) {
        return classesRepository.findAllByTeacherCount(currentUser.getId());
    }

    @Override
    public List<CLassesDTO> getAll(User currentUser) {
        return classesRepository.findAll().stream().map(classesMapper::toClassesDTO).collect(Collectors.toList());
    }
}
