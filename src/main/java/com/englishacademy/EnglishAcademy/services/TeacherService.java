package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.user.UserDTO;

import java.util.List;

public interface TeacherService {
    List<UserDTO> findAll();
}
