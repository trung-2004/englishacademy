package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.test_offline.TestOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_session.TestOfflineSessionDetailResult;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerOfflineStudent;
import com.englishacademy.EnglishAcademy.services.TestOfflineService;
import com.englishacademy.EnglishAcademy.services.impl.FileAudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-offline")
@CrossOrigin("*")
public class TestOfflineController {
    private final TestOfflineService testOfflineService;
    private final FileAudioService fileAudioService;
    public TestOfflineController(TestOfflineService testOfflineService, FileAudioService fileAudioService) {
        this.testOfflineService = testOfflineService;
        this.fileAudioService = fileAudioService;
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailTest(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        TestOfflineDetail testOfflineDetail = testOfflineService.getdetailTest(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOfflineDetail)
        );
    }

    @PostMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> submitTest(
            @RequestBody List<CreateAnswerOfflineStudent> submitTest,
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        testOfflineService.submitTest(slug, currentStudent.getId(), submitTest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("detail-score/{code}")
    ResponseEntity<ResponseObject> getDetailToScoreTest(@PathVariable("code") String code) {
        List<TestOfflineSessionDetailResult> testOfflineDetail = testOfflineService.getdetailToScoreTest(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOfflineDetail)
        );
    }

    @PostMapping("/file")
    ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        try{
            String image = fileAudioService.storeFile(file);
            return ResponseEntity.ok().body(image);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }
}
