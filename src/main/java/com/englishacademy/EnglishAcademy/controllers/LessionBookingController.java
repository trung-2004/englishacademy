package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.dtos.lessionBooking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;
import com.englishacademy.EnglishAcademy.services.ILessionBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LessionBookingController {
    private final ILessionBookingService lessionBookingService;

    public LessionBookingController(ILessionBookingService lessionBookingService) {
        this.lessionBookingService = lessionBookingService;
    }

    @GetMapping("/any/lession-booking")
    ResponseEntity<ResponseObject> getAll() {
        List<LessionBookingDTO> list = lessionBookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("/lession-booking")
    ResponseEntity<ResponseObject> insert(@RequestBody CreateLessionBooking createLessionBooking) {
        lessionBookingService.save(createLessionBooking);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
