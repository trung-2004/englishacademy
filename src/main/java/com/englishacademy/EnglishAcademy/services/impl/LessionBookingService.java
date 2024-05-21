package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.lessionBooking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.Booking;
import com.englishacademy.EnglishAcademy.entities.LessionBooking;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.LessionBookingMapper;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;
import com.englishacademy.EnglishAcademy.repositories.BookingRepository;
import com.englishacademy.EnglishAcademy.repositories.LessionBookingRepository;
import com.englishacademy.EnglishAcademy.services.ILessionBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessionBookingService implements ILessionBookingService {
    private final LessionBookingRepository lessionBookingRepository;
    private final BookingRepository bookingRepository;
    private final LessionBookingMapper lessionBookingMapper;

    @Override
    public List<LessionBookingDTO> findAll() {
        return lessionBookingRepository.findAll()
                .stream().map(lessionBookingMapper::toLessionBookingDTO).collect(Collectors.toList());
    }

    @Override
    public void save(CreateLessionBooking createLessionBooking) {
        Booking booking = bookingRepository.findById(createLessionBooking.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        System.out.println(booking.getTutor().getHourlyRate());
        LessionBooking lessionBooking = LessionBooking.builder()
                .booking(booking)
                .bookingTime(createLessionBooking.getBookingTime())
                .duration(createLessionBooking.getDuration())
                .status(0)
                .isPaid(false)
                .paymentMethod(null)
                .build();

        lessionBooking.setPrice((createLessionBooking.getDuration() / 60.0) * booking.getTutor().getHourlyRate());
        lessionBooking.setCreatedBy(booking.getTutor().getUser().getFullName());
        lessionBooking.setModifiedBy(booking.getTutor().getUser().getFullName());
        lessionBooking.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        lessionBooking.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        lessionBookingRepository.save(lessionBooking);
    }
}
