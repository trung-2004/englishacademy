package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.lession_booking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.LessionBookingMapper;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;
import com.englishacademy.EnglishAcademy.models.booking.UpdateLessionBooking;
import com.englishacademy.EnglishAcademy.repositories.BookingRepository;
import com.englishacademy.EnglishAcademy.repositories.LessionBookingRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.services.LessionBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessionBookingServiceImpl implements LessionBookingService {
    private final LessionBookingRepository lessionBookingRepository;
    private final BookingRepository bookingRepository;
    private final LessionBookingMapper lessionBookingMapper;
    private final TutorRepository tutorRepository;

    @Override
    public List<LessionBookingDTO> findAll() {
        return lessionBookingRepository.findAll()
                .stream().map(lessionBookingMapper::toLessionBookingDTO).collect(Collectors.toList());
    }

    @Override
    public void save(CreateLessionBooking createLessionBooking, User user) {
        Booking booking = bookingRepository.findById(createLessionBooking.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        LessionBooking lessionBooking = LessionBooking.builder()
                .booking(booking)
                .scheduledEndTime(createLessionBooking.getEndTime())
                .scheduledStartTime(createLessionBooking.getStartTime())
                .status(LessonBookingStatus.scheduled)
                .build();

        lessionBooking.setCreatedBy(user.getFullName());
        lessionBooking.setModifiedBy(user.getFullName());
        lessionBooking.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        lessionBooking.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        lessionBookingRepository.save(lessionBooking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LessionBookingDTO> findAllByTutor(User user) {
        List<LessionBookingDTO> lessionBookingDTOS = new ArrayList<>();
            for (Booking booking: tutorRepository.findByUser(user).getBookings()) {
                List<LessionBookingDTO> lessionBookingDTOList = booking.getLessionBookings()
                        .stream().map(lessionBookingMapper::toLessionBookingDTO).collect(Collectors.toList());
                lessionBookingDTOS.addAll(lessionBookingDTOList);
            }
        return lessionBookingDTOS;
    }

    @Override
    public List<LessionBookingDTO> findAllByBooking(Long bookingId, User user) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if (booking.getTutor().getUser().equals(user)){
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return lessionBookingRepository.findAllByBooking(booking)
                .stream().map(lessionBookingMapper::toLessionBookingDTO).collect(Collectors.toList());
    }

    @Override
    public void update(UpdateLessionBooking updateLessionBooking, User currentUser) {
        LessionBooking lessionBooking = lessionBookingRepository.findById(updateLessionBooking.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        lessionBooking.setPath(updateLessionBooking.getPath());
        lessionBooking.setModifiedBy(currentUser.getFullName());
        lessionBooking.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        lessionBookingRepository.save(lessionBooking);
    }

    @Override
    public LessionBookingDTO getDetail(Long id, Student currentUser) {
        LessionBooking lessionBooking = lessionBookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if (!lessionBooking.getBooking().getStudent().getId().equals(currentUser.getId())) throw new AppException(ErrorCode.NOTFOUND);
        return lessionBookingMapper.toLessionBookingDTO(lessionBooking);
    }
}
