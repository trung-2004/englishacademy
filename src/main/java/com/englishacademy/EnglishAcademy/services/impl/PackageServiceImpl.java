package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.packages.PackageDTO;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.PackagesMapper;
import com.englishacademy.EnglishAcademy.repositories.PackagesRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.services.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    private final PackagesRepository packagesRepository;
    private final TutorRepository  tutorRepository;
    private final PackagesMapper packagesMapper;

    @Override
    public List<PackageDTO> findAllByTutor(String code) {
        Tutor tutor = tutorRepository.findByCode(code);
        if (tutor == null) throw new AppException(ErrorCode.NOTFOUND);
        return packagesRepository.findAllByTutor(tutor).stream().map(packagesMapper::toPackageDTO).collect(Collectors.toList());
    }
}
