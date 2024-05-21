package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.entities.Booking;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.BookingMapper;
import com.englishacademy.EnglishAcademy.models.booking.CreateBooking;
import com.englishacademy.EnglishAcademy.repositories.BookingRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.services.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final TutorRepository tutorRepository;
    private final BookingMapper bookingMapper;
    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll().stream().map(bookingMapper::toBookingDTO).collect(Collectors.toList());
    }

    @Override
    public void save(List<CreateBooking> createBookingList, Student student) {
        for (CreateBooking createBooking: createBookingList) {
            Tutor tutor = tutorRepository.findById(createBooking.getTutorId())
                    .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOTFOUND));
            //tutor.getAvailabilities().stream()
            Booking booking = Booking.builder()
                    .bookingTime(createBooking.getBookingTime())
                    .description(createBooking.getDescription())
                    .student(student)
                    .tutor(tutor)
                    .duration(createBooking.getDuration())
                    .status(false)
                    .build();

            booking.setCreatedBy(tutor.getUser().getFullName());
            booking.setModifiedBy(tutor.getUser().getFullName());
            booking.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            booking.setModifiedDate(new Timestamp(System.currentTimeMillis()));
            bookingRepository.save(booking);
        }
    }

    @Override
    public List<BookingDTO> findAllByTutor(Tutor tutor) {
        return bookingRepository.findAllByTutor(tutor)
                .stream().map(bookingMapper::toBookingDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findAllByStudent(Student student) {
        return bookingRepository.findAllByStudent(student)
                .stream().map(bookingMapper::toBookingDTO).collect(Collectors.toList());
    }
}
