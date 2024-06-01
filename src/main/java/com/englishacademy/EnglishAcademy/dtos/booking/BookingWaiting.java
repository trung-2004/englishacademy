package com.englishacademy.EnglishAcademy.dtos.booking;

import com.englishacademy.EnglishAcademy.dtos.studentPackage.StudentPackageDTO;
import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingWaiting {
    List<StudentPackageDTO> studentPackageDTOS;
    List<SubscriptionDTO> subscriptionDTOS;
}
