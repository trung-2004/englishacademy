package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.availability.AvailabilityDTO;
import com.englishacademy.EnglishAcademy.mappers.AvailabilityMapper;
import com.englishacademy.EnglishAcademy.repositories.AvailabilityRepository;
import com.englishacademy.EnglishAcademy.services.IAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService implements IAvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;
    @Override
    public List<AvailabilityDTO> findAll() {
        return availabilityRepository.findAll().stream().map(availabilityMapper::availabilityToAvailabilityDTO).collect(Collectors.toList());
    }
}
