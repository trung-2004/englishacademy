package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.entities.User;

public interface SubscriptionService {
    void confirmStatus(Long id, User currentUser);

    void cancelStatus(Long id, User currentUser);
}
