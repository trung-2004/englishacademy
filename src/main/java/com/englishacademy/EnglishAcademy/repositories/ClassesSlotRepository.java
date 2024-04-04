package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Classes;
import com.englishacademy.EnglishAcademy.entities.ClassesSlot;
import com.englishacademy.EnglishAcademy.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesSlotRepository extends JpaRepository<ClassesSlot, Long> {
    ClassesSlot findByClassesAndSlot(Classes classes, Slot slot);
}
