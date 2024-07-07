package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.certificate.CertificateDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.certificate.CreateCertificate;
import com.englishacademy.EnglishAcademy.services.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("/certificate-online/complete-course/{courseId}")
    ResponseEntity<ResponseObject> createCertificate(@PathVariable("courseId") Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        CreateCertificate certificate = new CreateCertificate();
        certificate.setCourseId(courseId);
        certificate.setUserId(currentStudent.getId());
        CertificateDTO certificateDTO = certificateService.create(certificate);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", certificateDTO)
        );
    }

    @PostMapping("/certificate-offline/complete-course/{courseId}")
    ResponseEntity<ResponseObject> createCertificateOf(@PathVariable("courseId") Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        CreateCertificate certificate = new CreateCertificate();
        certificate.setCourseId(courseId);
        certificate.setUserId(currentStudent.getId());
        CertificateDTO certificateDTO = certificateService.createOf(certificate);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", certificateDTO)
        );
    }

    @GetMapping("/any/generate-certificate/{code}")
    ResponseEntity<ResponseObject> get(@PathVariable("code") String code) {
        CertificateDTO certificateDTO = certificateService.getCertificate(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", certificateDTO)
        );
    }

    @GetMapping("/generate-certificate/by-student")
    ResponseEntity<ResponseObject> getByStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        List<CertificateDTO> certificateDTO = certificateService.getCertificateList(currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", certificateDTO)
        );
    }

}
