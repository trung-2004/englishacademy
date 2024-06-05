package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.packages.PackageDTO;

import java.util.List;

public interface IPackageService {
    List<PackageDTO> findAllByTutor(String code);
}
