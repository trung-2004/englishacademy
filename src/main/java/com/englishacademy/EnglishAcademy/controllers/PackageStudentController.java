package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.student_package.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.services.PackageStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PackageStudentController {
    private final PackageStudentService packageStudentService;

    public PackageStudentController(PackageStudentService packageStudentService) {
        this.packageStudentService = packageStudentService;
    }

    @GetMapping("/package-student/student/{id}")
    ResponseEntity<ResponseObject> getDetailStudent(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        StudentPackageDTO studentPackageDTO = packageStudentService.getDetailByStudent(id, currentStudent);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", studentPackageDTO)
        );
    }

    @GetMapping("/package-student/tutor/{id}")
    ResponseEntity<ResponseObject> getDetailTutor(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        StudentPackageDTO studentPackageDTO = packageStudentService.getDetailByTutor(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", studentPackageDTO)
        );
    }

    @PutMapping("/package-student/confirm/{id}")
    ResponseEntity<ResponseObject> confirmStatus(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        packageStudentService.confirmStatus(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
    @PutMapping("/package-student/cancel/{id}")
    ResponseEntity<ResponseObject> cancelStatus(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        packageStudentService.cancelStatus(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
