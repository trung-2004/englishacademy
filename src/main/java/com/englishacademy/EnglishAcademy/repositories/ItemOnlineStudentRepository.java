package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.ItemOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOnlineStudentRepository extends JpaRepository<ItemOnlineStudent, Long> {
    ItemOnlineStudent findByItemOnlineAndStudent(ItemOnline itemOnline, Student student);
}
