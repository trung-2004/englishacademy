package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingResponse;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingWaiting;
import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import com.englishacademy.EnglishAcademy.dtos.student_package.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.*;
import com.englishacademy.EnglishAcademy.models.booking.CreateBooking;
import com.englishacademy.EnglishAcademy.models.mail.MailStructure;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.BookingService;
import com.englishacademy.EnglishAcademy.utils.JsonConverterUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONArray;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;
    private final AvailabilityRepository availabilityRepository;
    private final BookingMappers bookingMappers;
    private final LessionBookingMapper lessionBookingMapper;
    private final PackagesRepository packagesRepository;
    private final StudentPackageRepository studentPackageRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final StudentPackageMapper studentPackageMapper;
    private final StudentMapper studentMapper;
    private final MailService mailService;
    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll().stream().map(bookingMappers::toBookingDTO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = AppException.class)
    @Override
    public void save(CreateBooking createBooking, Student student) {
        Date now = new Timestamp(System.currentTimeMillis());
        // Convert lesson schedule to JSON
        JSONArray scheduleArray = new JSONArray();
//        for (CreateBooking.LessonDay lesson : createBooking.getLessonDays()) {
//            JSONObject schedule = new JSONObject();
//            schedule.put("dayOfWeek", lesson.getDayOfWeek());
//            schedule.put("startTime", lesson.getStartTime());
//            schedule.put("endTime", lesson.getEndTime());
//            scheduleArray.put(schedule);
//            Availability availability = availabilityRepository.findByStartTimeAndEndTimeAndDayOfWeek(lesson.getStartTime(), lesson.getEndTime(), lesson.getDayOfWeek());
//            if (availability == null || availability.isStatus()) throw new AppException(ErrorCode.NOTFOUND);
//            availability.setStatus(true);
//            availabilityRepository.save(availability);
//        }
        for (Long id : createBooking.getLessonDays()) {
            Availability availability = availabilityRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            if (availability == null || availability.isStatus()) throw new AppException(ErrorCode.NOTFOUND);
            availability.setStatus(true);
            availabilityRepository.save(availability);
            JSONObject schedule = new JSONObject();
            schedule.put("dayOfWeek", availability.getDayOfWeek());
            schedule.put("startTime", availability.getStartTime());
            schedule.put("endTime", availability.getEndTime());
            scheduleArray.put(schedule);
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
                    .purchaseDate(now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .lessonDays(scheduleArray.toString())
                    .price(packages.getHourlyRate())
                    .status(BookingStatus.pending)
                    .build();
            studentPackageRepository.save(studentPackage);

            MailStructure mailStructure = new MailStructure();
            mailStructure.setSubject("Tutor");
            mailStructure.setMessage("If a student has hired a tutor, please confirm");
            mailService.sendMail(studentPackage.getStudent().getEmail(), mailStructure);

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
                .stream().map(bookingMappers::toBookingDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findAllByStudent(Student student) {
        return bookingRepository.findAllByStudent(student)
                .stream().map(bookingMappers::toBookingDTO).collect(Collectors.toList());
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

    @Override
    public BookingResponse getDetailById(Long id, Student currentStudent) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

        BookingResponse bookingResponse = BookingResponse.builder()
                .id(booking.getId())
                .tutorId(booking.getTutor().getId())
                .studentId(booking.getStudent().getId())
                .studentName(booking.getStudent().getFullName())
                .tutorName(booking.getTutor().getUser().getFullName())
                .description(booking.getDescription())
                .createdBy(booking.getCreatedBy())
                .paymentId(booking.getPayment().getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .lessonDays(JsonConverterUtil.convertJsonToLessonDay(booking.getLessonDays()))
                .lessionBookingDTOS(booking.getLessionBookings().stream().map(lessionBookingMapper::toLessionBookingDTO).collect(Collectors.toList()))
                .createdDate(booking.getCreatedDate())
                .modifiedBy(booking.getModifiedBy())
                .modifiedDate(booking.getModifiedDate())
                .build();
        return bookingResponse;
    }

    @Override
    public BookingResponse getDetailByIdTutor(Long id, User currentUser) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

        BookingResponse bookingResponse = BookingResponse.builder()
                .id(booking.getId())
                .tutorId(booking.getTutor().getId())
                .studentId(booking.getStudent().getId())
                .studentName(booking.getStudent().getFullName())
                .tutorName(booking.getTutor().getUser().getFullName())
                .description(booking.getDescription())
                .createdBy(booking.getCreatedBy())
                .paymentId(booking.getPayment().getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .lessonDays(JsonConverterUtil.convertJsonToLessonDay(booking.getLessonDays()))
                .lessionBookingDTOS(booking.getLessionBookings().stream().map(lessionBookingMapper::toLessionBookingDTO).collect(Collectors.toList()))
                .createdDate(booking.getCreatedDate())
                .modifiedBy(booking.getModifiedBy())
                .modifiedDate(booking.getModifiedDate())
                .build();
        return bookingResponse;
    }

    @Override
    public int findCountAllStudentStuding(User currenUser) {
        Tutor tutor = tutorRepository.findByUser(currenUser);
        if (tutor == null) throw new AppException(ErrorCode.NOTFOUND);
        return bookingRepository.getCountStudentStudying(tutor.getId());
    }

    @Override
    public List<StudentDTO> findActiveStudentsByTutorId(User currenUser) {
        Tutor tutor = tutorRepository.findByUser(currenUser);
        if (tutor == null) throw new AppException(ErrorCode.NOTFOUND);
        return bookingRepository.findActiveStudentsByTutorId(tutor.getId())
                .stream().map(studentMapper::toStudentDTO).collect(Collectors.toList());
    }
}
