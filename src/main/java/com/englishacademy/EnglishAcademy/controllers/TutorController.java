package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorDetail;
import com.englishacademy.EnglishAcademy.models.tutor.CreateTutor;
import com.englishacademy.EnglishAcademy.services.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TutorController {
    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
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
//            @ModelAttribute CreateTutor createTutor
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
