package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.potential_customer.PotentialCustomerDTO;
import com.englishacademy.EnglishAcademy.models.potential_customer.PotentialCustomerCreate;

import java.util.List;

public interface PotentialCustomerService {
    List<PotentialCustomerDTO> getAll();

    PotentialCustomerDTO create(PotentialCustomerCreate potentialCustomerCreate);
}
