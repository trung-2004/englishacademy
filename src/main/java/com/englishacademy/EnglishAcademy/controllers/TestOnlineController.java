package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.testInput.TestInputDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudent;
import com.englishacademy.EnglishAcademy.services.ITestOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-online")
public class TestOnlineController {
    @Autowired
    private ITestOnlineService testOnlineService;

    @GetMapping("/detail/{slug}/{studentId}")
    ResponseEntity<ResponseObject> getDetailTest(
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ) {
        try {
            TestOnlineDetail testInputDetail = testOnlineService.getdetailTest(slug, studentId);
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
            testOnlineService.submitTest(slug, studentId, answersForStudents);
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
