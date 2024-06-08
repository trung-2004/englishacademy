package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.services.CourseOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-offline")
public class CourseOfflineController {
    @Autowired
    private CourseOfflineService courseOfflineService;
    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        List<CourseOfflineDTO> list = courseOfflineService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/get-by-class")
    ResponseEntity<ResponseObject> getAllCourseByClass() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        List<CourseOfflineDTO> list = courseOfflineService.findByStudent(currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailCourseByClass(
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        CourseOfflineDetail courseOfflineDetail = courseOfflineService.getDetail(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOfflineDetail)
        );
    }
}
