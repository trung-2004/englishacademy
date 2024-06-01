package com.englishacademy.EnglishAcademy.models.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePayment {
    private Integer bookingType;
    private Long id;
    private String paymentMethod;
    private Double price;
}
