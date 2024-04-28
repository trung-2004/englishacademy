package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.testOffline.TestOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.testSession.TestOfflineSessionDetailResult;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerOfflineStudent;
import com.englishacademy.EnglishAcademy.models.answerStudent.SubmitTest;
import com.englishacademy.EnglishAcademy.services.ITestOfflineService;
import com.englishacademy.EnglishAcademy.services.ITestOnlineService;
import com.englishacademy.EnglishAcademy.services.impl.FileAudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final ITestOfflineService testOfflineService;
    private final FileAudioService fileAudioService;
    public TestOfflineController(ITestOfflineService testOfflineService, FileAudioService fileAudioService) {
        this.testOfflineService = testOfflineService;
        this.fileAudioService = fileAudioService;
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailTest(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        Student currentStudent = (Student) auth.getPrincipal();
        TestOfflineDetail testOfflineDetail = testOfflineService.getdetailTest(slug, 1L);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOfflineDetail)
        );
    }

    @PostMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> submitTest(
            @RequestPart("submitTest") List<CreateAnswerOfflineStudent> submitTest,
            @PathVariable("slug") String slug
    ) {
        /*Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        Student currentStudent = (Student) auth.getPrincipal();*/
        try {
            testOfflineService.submitTest(slug, 1L, submitTest);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", "")
            );
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", "")
            );
        }
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
