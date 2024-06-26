package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.lession_booking.LessionBookingDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.booking.CreateLessionBooking;
import com.englishacademy.EnglishAcademy.models.booking.UpdateLessionBooking;
import com.englishacademy.EnglishAcademy.services.LessionBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LessionBookingController {
    private final LessionBookingService lessionBookingService;

    public LessionBookingController(LessionBookingService lessionBookingService) {
        this.lessionBookingService = lessionBookingService;
    }

    @GetMapping("/lession-booking")
    ResponseEntity<ResponseObject> getAll() {
        List<LessionBookingDTO> list = lessionBookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
    @GetMapping("/lession-booking/{booking_id}")
    ResponseEntity<ResponseObject> getAllByBooking(@PathVariable("booking_id") Long bookingId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<LessionBookingDTO> list = lessionBookingService.findAllByBooking(bookingId, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/lession-booking/detail/{id}")
    ResponseEntity<ResponseObject> getDetail(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentUser = (Student) auth.getPrincipal();
        LessionBookingDTO list = lessionBookingService.getDetail(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("/lession-booking")
    ResponseEntity<ResponseObject> insert(@RequestBody CreateLessionBooking createLessionBooking) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        lessionBookingService.save(createLessionBooking, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
    @PutMapping("/lession-booking")
    ResponseEntity<ResponseObject> update(@RequestBody UpdateLessionBooking updateLessionBooking) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        lessionBookingService.update(updateLessionBooking, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("/tutor/lession-booking")
    ResponseEntity<ResponseObject> getAllByTutor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<LessionBookingDTO> list = lessionBookingService.findAllByTutor(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    } 
}
