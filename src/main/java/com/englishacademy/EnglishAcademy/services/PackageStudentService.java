package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.student_package.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;

public interface PackageStudentService {
    void confirmStatus(Long id, User currentUser);
    void cancelStatus(Long id, User currentUser);

    StudentPackageDTO getDetailByStudent(Long id, Student currentStudent);

    StudentPackageDTO getDetailByTutor(Long id, User currentUser);
}
