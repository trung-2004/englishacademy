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
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;
    private final AvailabilityRepository availabilityRepository;
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

    @Transactional(rollbackFor = AppException.class)
    @Override
    public void save(CreateBooking createBooking, Student student) {
        Date now = new Timestamp(System.currentTimeMillis());
        // Convert lesson schedule to JSON
        JSONArray scheduleArray = new JSONArray();
        for (CreateBooking.LessonDay lesson : createBooking.getLessonDays()) {
            JSONObject schedule = new JSONObject();
            schedule.put("dayOfWeek", lesson.getDayOfWeek());
            schedule.put("startTime", lesson.getStartTime());
            schedule.put("endTime", lesson.getEndTime());
            scheduleArray.put(schedule);
            Availability availability = availabilityRepository.findByStartTimeAndEndTimeAndDayOfWeek(lesson.getStartTime(), lesson.getEndTime(), lesson.getDayOfWeek());
            if (availability != null && availability.isStatus()) throw new AppException(ErrorCode.NOTFOUND);
            availability.setStatus(true);
            availabilityRepository.save(availability);
        }
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
                    .lessonDays(scheduleArray.toString())
                    .price(packages.getHourlyRate())
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
                    .lessonDays(scheduleArray.toString())
                    .build();
            subscriptionRepository.save(subscription);
        } else {
            return;
        }
    }

    @Override
    public List<BookingDTO> findAllByTutor(User user) {
        Tutor tutor = tutorRepository.findByUser(user);
        if (tutor == null) throw new AppException(ErrorCode.NOTFOUND);
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
            List<StudentPackageDTO> studentPackageDTO = studentPackageRepository.findAllByPackages(packages)
                    .stream().map(studentPackageMapper::toStudentPackageDTO).toList();
            studentPackageDTOS.addAll(studentPackageDTO);
        }
        List<SubscriptionDTO> subscriptionDTOS = subscriptionRepository.findAllByTutor(tutor)
                .stream().map(subscriptionMapper::toSubscriptionDTO).collect(Collectors.toList());
        studentPackageDTOS.sort(Comparator.comparingLong(StudentPackageDTO::getId).reversed());
        subscriptionDTOS.sort(Comparator.comparingLong(SubscriptionDTO::getId).reversed());
        BookingWaiting bookingWaiting = BookingWaiting.builder()
                .studentPackageDTOS(studentPackageDTOS)
                .subscriptionDTOS(subscriptionDTOS)
                .build();
        return bookingWaiting;
    }

    @Override
    public BookingWaiting findAllWaitingByStudent(Student currentStudent) {
        Student student = studentRepository.findById(currentStudent.getId()).orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        List<StudentPackageDTO> studentPackageDTOS = studentPackageRepository.findAllByStudent(currentStudent)
                .stream().map(studentPackageMapper::toStudentPackageDTO).collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOS = subscriptionRepository.findAllByStudent(currentStudent)
                .stream().map(subscriptionMapper::toSubscriptionDTO).collect(Collectors.toList());
        studentPackageDTOS.sort(Comparator.comparingLong(StudentPackageDTO::getId).reversed());
        subscriptionDTOS.sort(Comparator.comparingLong(SubscriptionDTO::getId).reversed());
        BookingWaiting bookingWaiting = BookingWaiting.builder()
                .studentPackageDTOS(studentPackageDTOS)
                .subscriptionDTOS(subscriptionDTOS)
                .build();
        return bookingWaiting;
    }
}
