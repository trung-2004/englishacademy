package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findByCourseIdAndUserId(Long courseId, Long userId);
    Certificate findByCode(String code);
    List<Certificate> findByUserId(Long userId);
}
