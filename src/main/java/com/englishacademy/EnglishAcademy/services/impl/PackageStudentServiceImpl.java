package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.student_package.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.StudentPackageMapper;
import com.englishacademy.EnglishAcademy.models.mail.MailStructure;
import com.englishacademy.EnglishAcademy.repositories.StudentPackageRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.services.PackageStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PackageStudentServiceImpl implements PackageStudentService {
    private final StudentPackageRepository studentPackageRepository;
    private final StudentPackageMapper studentPackageMapper;
    private final TutorRepository tutorRepository;
    private final MailService mailService;

    @Override
    public void confirmStatus(Long id, User currentUser) {
        StudentPackage studentPackage = studentPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        Tutor tutor = tutorRepository.findByUser(currentUser);
        if (tutor == null || !studentPackage.getPackages().getTutor().equals(tutor) || studentPackage.getStatus().name().equals(BookingStatus.cancelled)) throw new AppException(ErrorCode.NOTFOUND);
        studentPackage.setStatus(BookingStatus.confirmed);
        studentPackageRepository.save(studentPackage);

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Tutor");
        mailStructure.setMessage("The teacher you hired has agreed to your registration, please go to the nearest payment");
        mailService.sendMail(studentPackage.getStudent().getEmail(), mailStructure);
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

    @Override
    public StudentPackageDTO getDetailByStudent(Long id, Student currentStudent) {
        StudentPackage studentPackage = studentPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if(!studentPackage.getStudent().getId().equals(currentStudent.getId())) throw new AppException(ErrorCode.NOTFOUND);
        StudentPackageDTO studentPackageDTO = studentPackageMapper.toStudentPackageDTO(studentPackage);
        if (studentPackage.getPayments().size() > 0) {
            studentPackageDTO.setStatus1(studentPackage.getPayments().get(0).isPaid());
        } else {
            studentPackageDTO.setStatus1(false);
        }
        return studentPackageDTO;
    }

    @Override
    public StudentPackageDTO getDetailByTutor(Long id, User currentUser) {
        StudentPackage studentPackage = studentPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if(!studentPackage.getPackages().getTutor().getUser().getId().equals(currentUser.getId())) throw new AppException(ErrorCode.NOTFOUND);
        return studentPackageMapper.toStudentPackageDTO(studentPackage);
    }
}
