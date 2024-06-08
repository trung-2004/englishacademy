package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import com.englishacademy.EnglishAcademy.entities.Subscription;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.utils.JsonConverterUtil;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public SubscriptionDTO toSubscriptionDTO(Subscription model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .id(model.getId())
                .tutorId(model.getTutor().getId())
                .studentId(model.getStudent().getId())
                .studentName(model.getStudent().getFullName())
                .lessonDays(JsonConverterUtil.convertJsonToLessonDay(model.getLessonDays()))
                .startTime(model.getStartTime())
                .endTime(model.getEndTime())
                .price(model.getPrice())
                .nextPaymentDate(model.getNextPaymentDate())
                .status(model.getStatus())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return subscriptionDTO;
    }
}
