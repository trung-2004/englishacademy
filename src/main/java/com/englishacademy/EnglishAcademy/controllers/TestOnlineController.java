package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online_student.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;
import com.englishacademy.EnglishAcademy.services.TestOnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test-online")
public class TestOnlineController {
    private final TestOnlineService testOnlineService;

    public TestOnlineController(TestOnlineService testOnlineService) {
        this.testOnlineService = testOnlineService;
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailTest(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        TestOnlineDetail testInputDetail = testOnlineService.getdetailTest(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testInputDetail)
        );
    }

    @PostMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> submitTest(
            @RequestBody SubmitTest submitTest,
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        String code = testOnlineService.submitTest(slug, currentStudent.getId(), submitTest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", code)
        );
    }

    @GetMapping("/result/{code}")
    ResponseEntity<ResponseObject> getResultTest(@PathVariable("code") String code) {
        TestOnlineStudentDTO testOnlineStudentDTO = testOnlineService.getresultTest(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOnlineStudentDTO)
        );
    }
}
