package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Packages;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagesRepository extends JpaRepository<Packages, Long> {
    List<Packages> findAllByTutor(Tutor tutor);
}
