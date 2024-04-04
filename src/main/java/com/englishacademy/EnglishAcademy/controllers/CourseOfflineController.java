package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.services.ICourseOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-offline")
public class CourseOfflineController {
    @Autowired
    private ICourseOfflineService courseOfflineService;
    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        List<CourseOfflineDTO> list = courseOfflineService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/get-by-class/{studentId}")
    ResponseEntity<ResponseObject> getAllCourseByClass(@PathVariable("studentId") Long studentId) {
        List<CourseOfflineDTO> list = courseOfflineService.findByStudent(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/detail/{slug}/{studentId}")
    ResponseEntity<ResponseObject> getDetailCourseByClass(
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ) {
        CourseOfflineDetail courseOfflineDetail = courseOfflineService.getDetail(slug, studentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOfflineDetail)
        );
    }

}
