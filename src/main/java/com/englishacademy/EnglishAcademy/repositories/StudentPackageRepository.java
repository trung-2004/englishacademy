package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import com.englishacademy.EnglishAcademy.entities.Packages;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPackageRepository extends JpaRepository<StudentPackage, Long> {
    List<StudentPackage> findAllByPackages(Packages packages);
    List<StudentPackage> findAllByStudent(Student student);
}
