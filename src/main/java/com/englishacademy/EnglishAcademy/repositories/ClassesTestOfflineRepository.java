package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Classes;
import com.englishacademy.EnglishAcademy.entities.ClassesTestOffline;
import com.englishacademy.EnglishAcademy.entities.TestOffline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesTestOfflineRepository extends JpaRepository<ClassesTestOffline, Long> {
    ClassesTestOffline findByTestOfflineAndClasses(TestOffline testOffline, Classes classes);
}
