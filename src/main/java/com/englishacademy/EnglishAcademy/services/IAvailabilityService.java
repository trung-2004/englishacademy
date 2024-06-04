package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.availability.AvailabilityDTO;

import java.util.List;

public interface IAvailabilityService {
    List<AvailabilityDTO> findAll();

    List<AvailabilityDTO> findALlByTutor(String code);
}
