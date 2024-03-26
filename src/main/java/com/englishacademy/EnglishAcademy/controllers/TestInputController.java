package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDetail;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudent;
import com.englishacademy.EnglishAcademy.repositories.TestInputRepository;
import com.englishacademy.EnglishAcademy.services.ITestInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-input")
public class TestInputController {
    @Autowired
    private ITestInputService testInputService;

    @GetMapping("/toiec")
    ResponseEntity<ResponseObject> getAllToiec() {
        try {
            List<TestInputDTO> list = testInputService.findAllToiec();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", list)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }

    @GetMapping("/ielts")
    ResponseEntity<ResponseObject> getAllIelts() {
        try {
            List<TestInputDTO> list = testInputService.findAllIelts();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", list)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getAllIelts(@PathVariable("slug") String slug) {
        try {
            TestInputDetail testInputDetail = testInputService.getdetailTest(slug);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", testInputDetail)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }

    @PostMapping("/detail/{slug}/{studentId}")
    ResponseEntity<ResponseObject> submitTest(
            @RequestBody List<CreateAnswerStudent> answersForStudents,
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ) {
        try {
            testInputService.submitTest(slug, studentId, answersForStudents);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", "")
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }

}
