package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.booking.LessonDay;
import com.englishacademy.EnglishAcademy.dtos.payment.PaymentDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.PaymentMapper;
import com.englishacademy.EnglishAcademy.models.payment.CreatePayment;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.PaymentService;
import com.englishacademy.EnglishAcademy.utils.JsonConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentPackageRepository studentPackageRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentMapper paymentMapper;
    private final BookingRepository bookingRepository;
    private final LessionBookingRepository lessionBookingRepository;

    @Override
    public PaymentDTO payment(CreatePayment createPayment, Student currentStudent) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (createPayment.getBookingType().equals(1)) {
            StudentPackage studentPackage = studentPackageRepository.findById(createPayment.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            if (studentPackage.getStatus().equals(BookingStatus.pending) || studentPackage.getStatus().equals(BookingStatus.cancelled)) {
                throw new AppException(ErrorCode.NOTFOUND);
            }
            Payment paymentExisting = paymentRepository.findByStudentPackage(studentPackage);
            if (paymentExisting != null) throw new AppException(ErrorCode.NOTFOUND);
            Payment payment = Payment.builder()
                    .paymentMethod(createPayment.getPaymentMethod())
                    .paymentDate(now.toLocalDateTime())
                    .studentPackage(studentPackage)
                    .price(createPayment.getPrice())
                    .isPaid(true)
                    .build();
            paymentRepository.save(payment);

            Booking booking = Booking.builder()
                    .payment(payment)
                    .student(currentStudent)
                    .tutor(studentPackage.getPackages().getTutor())
                    .startTime(null)
                    .endTime(null)
                    .status(BookingStatus.confirmed)
                    .description("dsoi ")
                    .lessonDays(studentPackage.getLessonDays())
                    .build();
            bookingRepository.save(booking);

            List<LessonDay> lessonDays = JsonConverterUtil.convertJsonToLessonDay(booking.getLessonDays());

            LocalDate today = LocalDate.now();
            DayOfWeek todayDayOfWeek = today.getDayOfWeek();
            List<LessionBooking> lessonBookings = new ArrayList<>();
            int sessionsCreated = 0;
            int numSessions = studentPackage.getPackages().getNumSessions();
            while (sessionsCreated < numSessions) {
                for (LessonDay schedule : lessonDays) {
                    if (sessionsCreated >= numSessions) {
                        break;
                    }

                    DayOfWeek lessonDayOfWeek = DayOfWeek.valueOf(schedule.getDayOfWeek().toUpperCase(Locale.ROOT));
                    LocalDate lessonDate = today.with(lessonDayOfWeek);

                    if (lessonDayOfWeek.compareTo(todayDayOfWeek) < 0) {
                        lessonDate = lessonDate.plusWeeks(1);
                    }

                    lessonDate = lessonDate.plusWeeks(sessionsCreated / lessonDays.size());
                    LocalDateTime startTime = lessonDate.atTime(schedule.getStartTime());
                    LocalDateTime endTime = lessonDate.atTime(schedule.getEndTime());

                    LessionBooking lessonBooking = new LessionBooking();
                    lessonBooking.setBooking(booking);
                    lessonBooking.setScheduledStartTime(startTime);
                    lessonBooking.setScheduledEndTime(endTime);
                    lessonBooking.setStatus(LessonBookingStatus.scheduled); // Adjust based on your enum
                    lessonBooking.setCreatedBy("Demo");
                    lessonBooking.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    lessonBooking.setModifiedDate(new Timestamp(System.currentTimeMillis()));
                    lessonBooking.setModifiedBy("Demo");
                    // Set other necessary fields

                    lessonBookings.add(lessonBooking);
                    sessionsCreated++;
                }
            }
            lessionBookingRepository.saveAll(lessonBookings);

            return paymentMapper.toPaymentDTO(payment);
        } else if (createPayment.getBookingType().equals(2)) {
            Subscription subscription = subscriptionRepository.findById(createPayment.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            if (subscription.getStatus().equals(BookingStatus.pending) || subscription.getStatus().equals(BookingStatus.cancelled)) {
                throw new AppException(ErrorCode.NOTFOUND);
            }
            Payment paymentExisting = paymentRepository.findBySubscription(subscription);
            if (paymentExisting != null) throw new AppException(ErrorCode.NOTFOUND);
            Payment payment = Payment.builder()
                    .paymentMethod(createPayment.getPaymentMethod())
                    .paymentDate(now.toLocalDateTime())
                    .subscription(subscription)
                    .price(createPayment.getPrice())
                    .isPaid(true)
                    .build();
            paymentRepository.save(payment);

            Booking booking = Booking.builder()
                    .payment(payment)
                    .student(currentStudent)
                    .tutor(subscription.getTutor())
                    .startTime(null)
                    .endTime(null)
                    .status(BookingStatus.confirmed)
                    .description("dsoi ")
                    .lessonDays(subscription.getLessonDays())
                    .build();
            bookingRepository.save(booking);

            return paymentMapper.toPaymentDTO(payment);
        } else {
            return null;
        }
    }
}
