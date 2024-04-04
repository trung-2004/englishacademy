package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnlineStudent.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.models.courseOnlineStudent.CreateCourseOnlineStudent;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-online-student")
public class CourseOnlineStudentController {
    @Autowired
    private ICourseOnlineStudentService courseOnlineStudentService;

    @PostMapping("")
    ResponseEntity<ResponseObject> createCourseOnlineStudent(@RequestBody CreateCourseOnlineStudent model) {
        try {
            CourseOnlineStudentDTO courseOnlineStudentDTO = courseOnlineStudentService.buyCourse(model);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", courseOnlineStudentDTO)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, 400, e.getMessage(), "")
            );
        }
    }

    @GetMapping("/check/{slug}/{studentId}")
    ResponseEntity<ResponseObject> checkSubCourse(
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ) {
        boolean checked = courseOnlineStudentService.checkCourseOnlineRegistered(slug, studentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", checked)
        );
    }
}
