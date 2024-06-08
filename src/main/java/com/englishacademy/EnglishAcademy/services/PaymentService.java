package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.payment.PaymentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.payment.CreatePayment;

public interface PaymentService {
    PaymentDTO payment(CreatePayment createPayment, Student currentStudent);
}
