package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.testOffline.TestOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.testOnline.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.testSession.TestOfflineSessionDetailResult;
import com.englishacademy.EnglishAcademy.services.ITestOfflineService;
import com.englishacademy.EnglishAcademy.services.ITestOnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-offline")
public class TestOfflineController {
    private final ITestOfflineService testOfflineService;
    public TestOfflineController(ITestOfflineService testOfflineService) {
        this.testOfflineService = testOfflineService;
    }

    @GetMapping("/detail/{slug}/{studentId}")
    ResponseEntity<ResponseObject> getDetailTest(
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ) {
        TestOfflineDetail testOfflineDetail = testOfflineService.getdetailTest(slug, studentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOfflineDetail)
        );
    }

    @GetMapping("detail-score/{code}")
    ResponseEntity<ResponseObject> getDetailToScoreTest(@PathVariable("code") String code) {
        List<TestOfflineSessionDetailResult> testOfflineDetail = testOfflineService.getdetailToScoreTest(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOfflineDetail)
        );
    }
}
