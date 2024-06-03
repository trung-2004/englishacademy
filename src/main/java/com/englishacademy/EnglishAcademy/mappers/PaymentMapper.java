package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.payment.PaymentDTO;
import com.englishacademy.EnglishAcademy.entities.Payment;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDTO toPaymentDTO(Payment model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(model.getId())
                .paymentDate(model.getPaymentDate())
                .paymentMethod(model.getPaymentMethod())
                .isPaid(model.isPaid())
                .price(model.getPrice())
                //.studentPackagesId(model.getStudentPackage().getId())
                //.subscriptionId(model.getSubscription().getId())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        if (model.getStudentPackage() != null) {
            paymentDTO.setStudentPackagesId(model.getStudentPackage().getId());
        }
        if (model.getSubscription() != null) {
            paymentDTO.setSubscriptionId(model.getSubscription().getId());
        }
        return paymentDTO;
    }
}
