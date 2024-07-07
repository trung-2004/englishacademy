package com.englishacademy.EnglishAcademy.models.potential_customer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PotentialCustomerCreate {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Phone number cannot be empty")
    private String phone;
}
