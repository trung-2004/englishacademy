package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.payment.PaymentDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.PaymentMapper;
import com.englishacademy.EnglishAcademy.models.payment.CreatePayment;
import com.englishacademy.EnglishAcademy.repositories.BookingRepository;
import com.englishacademy.EnglishAcademy.repositories.PaymentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentPackageRepository;
import com.englishacademy.EnglishAcademy.repositories.SubscriptionRepository;
import com.englishacademy.EnglishAcademy.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentPackageRepository studentPackageRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentMapper paymentMapper;
    private final BookingRepository bookingRepository;

    @Override
    public PaymentDTO payment(CreatePayment createPayment, Student currentStudent) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (createPayment.getBookingType().equals(1)) {
            StudentPackage studentPackage = studentPackageRepository.findById(createPayment.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            if (studentPackage.getStatus().equals(BookingStatus.pending) || studentPackage.getStatus().equals(BookingStatus.cancelled)) {
                throw new AppException(ErrorCode.NOTFOUND);
            }
            Payment payment = Payment.builder()
                    .paymentMethod(createPayment.getPaymentMethod())
                    .paymentDate(now)
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

            return paymentMapper.toPaymentDTO(payment);
        } else if (createPayment.getBookingType().equals(2)) {
            Subscription subscription = subscriptionRepository.findById(createPayment.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            if (subscription.getStatus().equals(BookingStatus.pending) || subscription.getStatus().equals(BookingStatus.cancelled)) {
                throw new AppException(ErrorCode.NOTFOUND);
            }
            Payment payment = Payment.builder()
                    .paymentMethod(createPayment.getPaymentMethod())
                    .paymentDate(now)
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
