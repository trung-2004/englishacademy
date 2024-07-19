package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Booking;
import com.englishacademy.EnglishAcademy.entities.LessionBooking;
import com.englishacademy.EnglishAcademy.entities.LessonBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessionBookingRepository extends JpaRepository<LessionBooking, Long> {
    List<LessionBooking> findAllByBooking(Booking booking);
    Optional<LessionBooking> findByCode(String code);
    Optional<LessionBooking> findByCodeAndStatus(String code, LessonBookingStatus status);
    Optional<LessionBooking> findByPathAndStatus(String path, LessonBookingStatus status);
}
