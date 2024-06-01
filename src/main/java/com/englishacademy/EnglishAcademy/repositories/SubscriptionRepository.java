package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import com.englishacademy.EnglishAcademy.entities.Subscription;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByTutorAndStatus(Tutor tutor, BookingStatus bool);
}
