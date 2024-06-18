package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingDTO;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingResponse;
import com.englishacademy.EnglishAcademy.dtos.booking.BookingWaiting;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.booking.CreateBooking;
import com.englishacademy.EnglishAcademy.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/any/booking")
    ResponseEntity<ResponseObject> getAll() {
        List<BookingDTO> list = bookingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/student/booking")
    ResponseEntity<ResponseObject> getAllByStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();

        List<BookingDTO> list = bookingService.findAllByStudent(currentStudent);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/student/booking/{id}")
    ResponseEntity<ResponseObject> getDetail(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        BookingResponse bookingResponse = bookingService.getDetailById(id, currentStudent);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", bookingResponse)
        );
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/tutor/booking")
    ResponseEntity<ResponseObject> getAllByTutor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<BookingDTO> list = bookingService.findAllByTutor(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("/booking")
    ResponseEntity<ResponseObject> insert(@RequestBody CreateBooking createBookingList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        bookingService.save(createBookingList, currentStudent);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("/tutor/booking-waiting")
    ResponseEntity<ResponseObject> getAllWaitingByTutor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        BookingWaiting bookingWaiting = bookingService.findAllWaitingByTutor(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", bookingWaiting)
        );
    }

    @GetMapping("/student/booking-waiting")
    ResponseEntity<ResponseObject> getAllWaitingByStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();

        BookingWaiting bookingWaiting = bookingService.findAllWaitingByStudent(currentStudent);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", bookingWaiting)
        );
    }
}
