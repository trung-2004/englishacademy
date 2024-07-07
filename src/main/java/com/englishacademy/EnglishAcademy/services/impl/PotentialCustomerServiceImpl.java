package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.potential_customer.PotentialCustomerDTO;
import com.englishacademy.EnglishAcademy.entities.PotentialCustomer;
import com.englishacademy.EnglishAcademy.mappers.PotentialCustomerMapper;
import com.englishacademy.EnglishAcademy.models.potential_customer.PotentialCustomerCreate;
import com.englishacademy.EnglishAcademy.repositories.PotentialCustomerRepository;
import com.englishacademy.EnglishAcademy.services.PotentialCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PotentialCustomerServiceImpl implements PotentialCustomerService {
    private final PotentialCustomerRepository potentialCustomerRepository;
    private final PotentialCustomerMapper potentialCustomerMapper;

    @Override
    public List<PotentialCustomerDTO> getAll() {
        return potentialCustomerRepository.findAll().stream().map(potentialCustomerMapper::toPotentialCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public PotentialCustomerDTO create(PotentialCustomerCreate potentialCustomerCreate) {
        PotentialCustomer potentialCustomerExisting = potentialCustomerRepository.findByEmailAAndPhone(potentialCustomerCreate.getEmail(), potentialCustomerCreate.getPhone());
        if (potentialCustomerExisting == null) {
            PotentialCustomer potentialCustomer = PotentialCustomer.builder()
                    .email(potentialCustomerCreate.getEmail())
                    .phone(potentialCustomerCreate.getPhone())
                    .fullName(potentialCustomerExisting.getFullName())
                    .status(0)
                    .createdBy("Demo")
                    .modifiedBy("Demo")
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .modifiedDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            potentialCustomerRepository.save(potentialCustomer);
            return potentialCustomerMapper.toPotentialCustomerDTO(potentialCustomer);
        }
        return potentialCustomerMapper.toPotentialCustomerDTO(potentialCustomerExisting);
    }

}
