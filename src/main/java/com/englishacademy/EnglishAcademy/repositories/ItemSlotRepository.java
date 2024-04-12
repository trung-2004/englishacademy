package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.ClassesSlot;
import com.englishacademy.EnglishAcademy.entities.ItemSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSlotRepository extends JpaRepository<ItemSlot, Long> {
    List<ItemSlot> findAllByClassesSlot(ClassesSlot classesSlot);
    ItemSlot findBySlug(String slug);
}
