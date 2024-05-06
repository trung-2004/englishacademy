package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.entities.ItemSlot;
import com.englishacademy.EnglishAcademy.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerStudentItemSlotRepository extends JpaRepository<AnswerStudentItemSlot, Long> {
    AnswerStudentItemSlot findByStudentAndItemSlot(Student student, ItemSlot itemSlot);
    List<AnswerStudentItemSlot> findAllByStudentAndItemSlot(Student student, ItemSlot itemSlot);
}
