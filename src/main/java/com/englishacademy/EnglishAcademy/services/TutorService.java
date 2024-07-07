package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDetail;
import com.englishacademy.EnglishAcademy.models.tutor.CreateTutor;

import java.util.List;

public interface TutorService {
    List<TutorDTO> findAll();
    TutorDetail getDetail(String code);
    void save(CreateTutor createTutor);
    List<TutorDTO> getTutorTop6();
}
