package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.lession_booking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;
import com.englishacademy.EnglishAcademy.models.booking.UpdateLessionBooking;

import java.util.List;

public interface LessionBookingService {
    List<LessionBookingDTO> findAll();
    void save(CreateLessionBooking createLessionBooking, User user);
    List<LessionBookingDTO> findAllByTutor(User user);
    List<LessionBookingDTO> findAllByBooking(Long bookingId, User user);
    void update(UpdateLessionBooking updateLessionBooking, User currentUser);
    LessionBookingDTO getDetail(Long id, Student currentUser);
    boolean check(String code, Student currentUser);
    void delete(List<Long> ids);
    void updateStatusComplete(Long id, User currentUser);
    void updateStatusInprocess(Long id, User currentUser);
    void updateStatusCancel(Long id, User currentUser);
    void updateStatusRescheduled(Long id, User currentUser);
}
