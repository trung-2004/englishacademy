package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.StudentPackageRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.services.PackageStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackageStudentServiceImpl implements PackageStudentService {
    private final StudentPackageRepository studentPackageRepository;
    private final TutorRepository tutorRepository;

    @Override
    public void confirmStatus(Long id, User currentUser) {
        StudentPackage studentPackage = studentPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        Tutor tutor = tutorRepository.findByUser(currentUser);
        if (tutor == null || !studentPackage.getPackages().getTutor().equals(tutor) || studentPackage.getStatus().name().equals(BookingStatus.cancelled)) throw new AppException(ErrorCode.NOTFOUND);
        studentPackage.setStatus(BookingStatus.confirmed);
        studentPackageRepository.save(studentPackage);
    }

    @Override
    public void cancelStatus(Long id, User currentUser) {
        StudentPackage studentPackage = studentPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        Tutor tutor = tutorRepository.findByUser(currentUser);
        if (tutor == null || !studentPackage.getPackages().getTutor().equals(tutor)) throw new AppException(ErrorCode.NOTFOUND);
        studentPackage.setStatus(BookingStatus.cancelled);
        studentPackageRepository.save(studentPackage);
    }
}
