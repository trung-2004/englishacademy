package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Availability;
import com.englishacademy.EnglishAcademy.entities.DayOfWeek;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAllByTutorAndStatus(Tutor tutor, boolean bool);
    Availability findByStartTimeAndEndTimeAndDayOfWeek(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek);
}
