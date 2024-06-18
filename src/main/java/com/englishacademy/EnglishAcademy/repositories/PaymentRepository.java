package com.englishacademy.EnglishAcademy.repositories;

import com.englishacademy.EnglishAcademy.entities.Payment;
import com.englishacademy.EnglishAcademy.entities.StudentPackage;
import com.englishacademy.EnglishAcademy.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByStudentPackage(StudentPackage studentPackage);
    Payment findBySubscription(Subscription subscription);
}
