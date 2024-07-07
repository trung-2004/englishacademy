package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.potential_customer.PotentialCustomerDTO;
import com.englishacademy.EnglishAcademy.entities.PotentialCustomer;
import org.springframework.stereotype.Component;

@Component
public class PotentialCustomerMapper {
    public PotentialCustomerDTO toPotentialCustomerDTO(PotentialCustomer potentialCustomer){
        if(potentialCustomer == null){
            return null;
        }
        PotentialCustomerDTO potentialCustomerDTO = PotentialCustomerDTO.builder()
                .id(potentialCustomer.getId())
                .fullname(potentialCustomer.getFullName())
                .email(potentialCustomer.getEmail())
                .phone(potentialCustomer.getPhone())
                .status(potentialCustomer.getStatus())
                .createdBy(potentialCustomer.getCreatedBy())
                .createdDate(potentialCustomer.getCreatedDate())
                .modifiedBy(potentialCustomer.getModifiedBy())
                .modifiedDate(potentialCustomer.getModifiedDate())
                .build();
        return potentialCustomerDTO;
    }
}
