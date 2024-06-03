package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.lessionBooking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;
import com.englishacademy.EnglishAcademy.models.booking.UpdateLessionBooking;

import java.util.List;

public interface ILessionBookingService {
    List<LessionBookingDTO> findAll();

    void save(CreateLessionBooking createLessionBooking, User user);

    List<LessionBookingDTO> findAllByTutor(User user);

    List<LessionBookingDTO> findAllByBooking(Long bookingId, User user);

    void update(UpdateLessionBooking updateLessionBooking, User currentUser);
}
