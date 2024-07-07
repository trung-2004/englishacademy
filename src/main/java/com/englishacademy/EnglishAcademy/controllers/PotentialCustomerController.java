package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.potential_customer.PotentialCustomerDTO;
import com.englishacademy.EnglishAcademy.models.potential_customer.PotentialCustomerCreate;
import com.englishacademy.EnglishAcademy.services.PotentialCustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/potential-customer")
@RequiredArgsConstructor
public class PotentialCustomerController {
    private final PotentialCustomerService potentialCustomerService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMISSIONS', 'ADMIN')")
    ResponseEntity<ResponseObject> getAllCustomers() {
        List<PotentialCustomerDTO> potentialCustomerDTOS = potentialCustomerService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", potentialCustomerDTOS)
        );
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    ResponseEntity<ResponseObject> create(@RequestBody @Valid PotentialCustomerCreate potentialCustomerCreate) {
        PotentialCustomerDTO potentialCustomerDTO = potentialCustomerService.create(potentialCustomerCreate);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", potentialCustomerDTO)
        );
    }
}
