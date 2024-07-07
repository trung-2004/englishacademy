package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findAllByStatus(boolean b);
    Tutor findByCode(String code);
    Tutor findByUser(User user);

    @Query(value = "SELECT t.* FROM tutor t JOIN (SELECT tutor_id, COUNT(*) AS booking_count FROM booking GROUP BY tutor_id) b ON t.id = b.tutor_id ORDER BY b.booking_count DESC LIMIT 6", nativeQuery = true)
    List<Tutor> findTop6Tutor();
}
