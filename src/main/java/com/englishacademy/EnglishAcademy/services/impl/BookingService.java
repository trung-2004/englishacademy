package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingWaiting;
import com.englishacademy.EnglishAcademy.dtos.studentPackage.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.BookingMapper;
import com.englishacademy.EnglishAcademy.mappers.StudentPackageMapper;
import com.englishacademy.EnglishAcademy.mappers.SubscriptionMapper;
import com.englishacademy.EnglishAcademy.models.booking.CreateBooking;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.threeten.bp.temporal.ChronoUnit;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final TutorRepository tutorRepository;
    private final BookingMapper bookingMapper;
    private final PackagesRepository packagesRepository;
    private final StudentPackageRepository studentPackageRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final StudentPackageMapper studentPackageMapper;
    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll().stream().map(bookingMapper::toBookingDTO).collect(Collectors.toList());
    }

    @Override
    public void save(CreateBooking createBooking, Student student) {
        Date now = new Timestamp(System.currentTimeMillis());
        Tutor tutor = tutorRepository.findById(createBooking.getTutorId())
                .orElseThrow(() -> new AppException(ErrorCode.TUTOR_NOTFOUND));
        if (createBooking.getTypeBooking().equals(1)){
            Packages packages = packagesRepository.findById(createBooking.getPackageId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            StudentPackage studentPackage = StudentPackage.builder()
                    .packages(packages)
                    .student(student)
                    .remainingSessions(packages.getNumSessions())
                    .purchaseDate(now)
                    .lessonDays(createBooking.getLessonDays())
                    .status(BookingStatus.pending)
                    .build();
            studentPackageRepository.save(studentPackage);

        } else if (createBooking.getTypeBooking().equals(2)){
            // Sử dụng Calendar để tính ngày của tháng tiếp theo
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.MONTH, 1);
            // Lấy Timestamp của ngày tháng tiếp theo
            Timestamp endTime = new Timestamp(cal.getTimeInMillis());
            Subscription subscription = Subscription.builder()
                    .tutor(tutor)
                    .student(student)
                    .startTime(now)
                    .endTime(endTime)
                    .nextPaymentDate(endTime)
                    .price(tutor.getHourlyRate())
                    .status(BookingStatus.pending)
                    .lessonDays(createBooking.getLessonDays())
                    .build();
            subscriptionRepository.save(subscription);
        } else {
            return;
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

    @Override
    public BookingWaiting findAllWaitingByTutor(User user) {
        Tutor tutor = tutorRepository.findByUser(user);
        if (tutor == null) throw new AppException(ErrorCode.NOTFOUND);
        List<StudentPackageDTO> studentPackageDTOS = new ArrayList<>();
        for (Packages packages: tutor.getPackages()) {
            List<StudentPackageDTO> studentPackageDTO = studentPackageRepository.findAllByPackagesAndStatus(packages, BookingStatus.pending)
                    .stream().map(studentPackageMapper::toStudentPackageDTO).toList();
            studentPackageDTOS.addAll(studentPackageDTO);
        }
        List<SubscriptionDTO> subscriptionDTOS = subscriptionRepository.findAllByTutorAndStatus(tutor, BookingStatus.pending)
                .stream().map(subscriptionMapper::toSubscriptionDTO).collect(Collectors.toList());
        BookingWaiting bookingWaiting = BookingWaiting.builder()
                .studentPackageDTOS(studentPackageDTOS)
                .subscriptionDTOS(subscriptionDTOS)
                .build();
        return bookingWaiting;
    }
}
