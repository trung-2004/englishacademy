package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.payment.PaymentDTO;
import com.englishacademy.EnglishAcademy.entities.Payment;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import com.englishacademy.EnglishAcademy.entities.Subscription;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.PaymentMapper;
import com.englishacademy.EnglishAcademy.models.payment.CreatePayment;
import com.englishacademy.EnglishAcademy.repositories.PaymentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentPackageRepository;
import com.englishacademy.EnglishAcademy.repositories.SubscriptionRepository;
import com.englishacademy.EnglishAcademy.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentPackageRepository studentPackageRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO payment(CreatePayment createPayment, Student currentStudent) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (createPayment.getBookingType().equals(1)) {
            StudentPackage studentPackage = studentPackageRepository.findById(createPayment.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            Payment payment = Payment.builder()
                    .paymentMethod(createPayment.getPaymentMethod())
                    .paymentDate(now)
                    .studentPackage(studentPackage)
                    .price(createPayment.getPrice())
                    .isPaid(true)
                    .build();
            paymentRepository.save(payment);
            return null;
        } else if (createPayment.getBookingType().equals(2)) {
            Subscription subscription = subscriptionRepository.findById(createPayment.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
            Payment payment = Payment.builder()
                    .paymentMethod(createPayment.getPaymentMethod())
                    .paymentDate(now)
                    .subscription(subscription)
                    .price(createPayment.getPrice())
                    .isPaid(true)
                    .build();
            paymentRepository.save(payment);
            return null;
        } else {
            return null;
        }
    }
}
