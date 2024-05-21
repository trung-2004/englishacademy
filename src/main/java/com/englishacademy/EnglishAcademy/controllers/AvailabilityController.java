package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.availability.AvailabilityDTO;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.services.IAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class AvailabilityController {
    private final IAvailabilityService availabilityService;

    public AvailabilityController(IAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/any/availability")
    ResponseEntity<ResponseObject> getAll() {
        List<AvailabilityDTO> list = availabilityService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
}
