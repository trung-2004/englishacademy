package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.entities.BookingStatus;
import com.englishacademy.EnglishAcademy.entities.Subscription;
import com.englishacademy.EnglishAcademy.entities.Tutor;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.SubscriptionRepository;
import com.englishacademy.EnglishAcademy.repositories.TutorRepository;
import com.englishacademy.EnglishAcademy.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final TutorRepository tutorRepository;

    @Override
    public void confirmStatus(Long id, User currentUser) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        Tutor tutor = tutorRepository.findByUser(currentUser);
        if (tutor == null || !subscription.getTutor().equals(tutor)) throw new AppException(ErrorCode.NOTFOUND);
        subscription.setStatus(BookingStatus.confirmed);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void cancelStatus(Long id, User currentUser) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        Tutor tutor = tutorRepository.findByUser(currentUser);
        if (tutor == null || !subscription.getTutor().equals(tutor)) throw new AppException(ErrorCode.NOTFOUND);
        subscription.setStatus(BookingStatus.cancelled);
        subscriptionRepository.save(subscription);
    }
}
