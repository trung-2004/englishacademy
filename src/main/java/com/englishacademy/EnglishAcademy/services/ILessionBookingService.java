package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.lessionBooking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;

import java.util.List;

public interface ILessionBookingService {
    List<LessionBookingDTO> findAll();

    void save(CreateLessionBooking createLessionBooking);
}
