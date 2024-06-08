package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.subscription.SubscriptionDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/subscription/student/{id}")
    ResponseEntity<ResponseObject> getDetailStudent(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        SubscriptionDTO subscriptionDTO = subscriptionService.getDetailByStudent(id, currentStudent);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subscriptionDTO)
        );
    }

    @GetMapping("/subscription/tutor/{id}")
    ResponseEntity<ResponseObject> getDetailTutor(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        SubscriptionDTO subscriptionDTO = subscriptionService.getDetailByTutor(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subscriptionDTO)
        );
    }

    @PutMapping("/subscription/confirm/{id}")
    ResponseEntity<ResponseObject> confirmStatus(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        subscriptionService.confirmStatus(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
    @PutMapping("/subscription/cancel/{id}")
    ResponseEntity<ResponseObject> cancelStatus(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        subscriptionService.cancelStatus(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
