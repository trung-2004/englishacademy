package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDetail;
import com.englishacademy.EnglishAcademy.models.availabilitiy.CreateAvailability;
import com.englishacademy.EnglishAcademy.models.tutor.CreateTutor;
import com.englishacademy.EnglishAcademy.services.ITutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TutorController {
    private final ITutorService tutorService;

    public TutorController(ITutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("/any/tutor")
    ResponseEntity<ResponseObject> getAll() {
        List<TutorDTO> list = tutorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/any/tutor-detail/{code}")
    ResponseEntity<ResponseObject> getDetail(@PathVariable("code") String code) {
        TutorDetail tutorDetail = tutorService.getDetail(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", tutorDetail)
        );
    }

    @PostMapping("/any/tutor/hello")
    ResponseEntity<ResponseObject> insert(
            @RequestParam("userId") Long userId,
            @RequestParam("phone") String phone,
            @RequestParam("level") Integer level,
            @RequestParam("hourlyRate") Double hourlyRate,
            @RequestParam("address") String address,
            @RequestParam("cetificate") String cetificate,
            @RequestParam("experience") String experience,
            @RequestParam("teachingSubject") String teachingSubject,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        CreateTutor createTutor = new CreateTutor();
        createTutor.setUserId(userId);
        createTutor.setPhone(phone);
        createTutor.setLevel(level);
        createTutor.setAddress(address);
        createTutor.setCetificate(cetificate);
        createTutor.setAvatar(avatar);
        createTutor.setExperience(experience);
        createTutor.setTeachingSubject(teachingSubject);
        createTutor.setHourlyRate(hourlyRate);

        tutorService.save(createTutor);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
