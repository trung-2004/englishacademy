package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;

public interface SubscriptionService {
    void confirmStatus(Long id, User currentUser);

    void cancelStatus(Long id, User currentUser);

    SubscriptionDTO getDetailByStudent(Long id, Student currentStudent);

    SubscriptionDTO getDetailByTutor(Long id, User currentUser);
}
