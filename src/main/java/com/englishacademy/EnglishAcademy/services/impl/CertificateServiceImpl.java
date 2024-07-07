package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.certificate.CertificateDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.certificate.CreateCertificate;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CourseOnlineRepository courseRepository;
    private final CourseOfflineRepository courseOfflineRepository;
    private final StudentRepository studentRepository;
    private final TestOnlineStudentRepository testOnlineStudentRepository;
    private final TestOfflineStudentRepository testOfflineStudentRepository;

    @Override
    public CertificateDTO create(CreateCertificate model) {
        CourseOnline course = courseRepository.findById(model.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        Certificate certificateExisting = certificateRepository.findByCourseIdAndUserId(course.getId(), model.getUserId());
        if (certificateExisting != null) {
            return toCertificateDTO(certificateExisting);
        }
        // student
        Student student = studentRepository.findById(model.getUserId()).orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        // check thi passed
        for (TopicOnline topicOnline : course.getTopicOnlines()) {
            for (TestOnline testOnline : topicOnline.getTestOnlines()) {
                TestOnlineStudent testOnlineStudent = testOnlineStudentRepository.findByTestOnlineAndStudentAndStatus(testOnline, student, true);
                if (testOnlineStudent == null) {
                    throw new AppException(ErrorCode.CERTIFICATE_QUALIFIED);
                }
            }
        }
        Certificate certificate = Certificate.builder()
                .courseId(course.getId())
                .userId(model.getUserId())
                .code(TestInputServiceImpl.generateRandomString(8))
                .issuedDate(new Timestamp(System.currentTimeMillis()))
                .downloadsCount(0)
                .fullName(student.getFullName())
                .createdBy(student.getFullName())
                .modifiedBy(student.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        certificateRepository.save(certificate);

        return toCertificateDTO(certificate);
    }

    @Override
    public CertificateDTO createOf(CreateCertificate model) {
        CourseOffline course = courseOfflineRepository.findById(model.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        Certificate certificateExisting = certificateRepository.findByCourseIdAndUserId(course.getId(), model.getUserId());
        if (certificateExisting != null) {
            return toCertificateDTO(certificateExisting);
        }
        // student
        Student student = studentRepository.findById(model.getUserId()).orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        // check thi passed
        for (Subject subject : course.getSubjects()) {
            for (TestOffline testOnline : subject.getTestOfflines()) {
                TestOfflineStudent testOnlineStudent = testOfflineStudentRepository.findByTestOfflineAndStudentAndStatus(testOnline, student, true);
                if (testOnlineStudent == null) {
                    throw new AppException(ErrorCode.CERTIFICATE_QUALIFIED);
                }
            }
        }
        Certificate certificate = Certificate.builder()
                .courseId(course.getId())
                .userId(model.getUserId())
                .issuedDate(new Timestamp(System.currentTimeMillis()))
                .downloadsCount(0)
                .fullName(student.getFullName())
                .createdBy(student.getFullName())
                .modifiedBy(student.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        certificateRepository.save(certificate);

        return toCertificateDTO(certificate);
    }

    @Override
    public CertificateDTO getCertificate(String code) {
        Certificate certificate = certificateRepository.findByCode(code);
        if (certificate == null) {
            throw new AppException(ErrorCode.CERTIFICATE_NOTFOUND);
        }
        return toCertificateDTO(certificate);
    }

    @Override
    public List<CertificateDTO> getCertificateList(Long id) {
        List<Certificate> list = certificateRepository.findByUserId(id);
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        for (Certificate certificate: list){
            CertificateDTO certificateDTO = toCertificateDTO(certificate);
            certificateDTOList.add(certificateDTO);
        }
        return certificateDTOList;
    }

    private CertificateDTO toCertificateDTO(Certificate certificate){
        if (certificate == null) return null;
        CourseOnline courseOnline = courseRepository.findById(certificate.getCourseId()).orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(certificate.getId())
                .courseId(courseOnline.getId())
                .courseName(courseOnline.getName())
                .fullName(certificate.getFullName())
                .userId(certificate.getUserId())
                .issuedDate(certificate.getIssuedDate())
                .createdBy(certificate.getCreatedBy())
                .modifiedBy(certificate.getModifiedBy())
                .createdDate(certificate.getCreatedDate())
                .modifiedDate(certificate.getModifiedDate())
                .build();
        return certificateDTO;
    }
}
