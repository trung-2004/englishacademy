package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> findAll();
}
