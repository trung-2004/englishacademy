package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.SubscriptionMapper;
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
    private final SubscriptionMapper subscriptionMapper;

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

    @Override
    public SubscriptionDTO getDetailByStudent(Long id, Student currentStudent) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if(!subscription.getStudent().getId().equals(currentStudent.getId())) throw new AppException(ErrorCode.NOTFOUND);
        return subscriptionMapper.toSubscriptionDTO(subscription);
    }

    @Override
    public SubscriptionDTO getDetailByTutor(Long id, User currentUser) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        if(!subscription.getTutor().getUser().getId().equals(currentUser.getId())) throw new AppException(ErrorCode.NOTFOUND);
        return subscriptionMapper.toSubscriptionDTO(subscription);

    }
}
