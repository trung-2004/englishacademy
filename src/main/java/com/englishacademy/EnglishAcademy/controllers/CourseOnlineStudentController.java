package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineStudentDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.student.StudentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.course_online_student.CreateCourseOnlineStudent;
import com.englishacademy.EnglishAcademy.services.CourseOnlineStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-online-student")
public class CourseOnlineStudentController {
    @Autowired
    private CourseOnlineStudentService courseOnlineStudentService;

    @GetMapping("/history")
    ResponseEntity<ResponseObject> getAll() {
        List<CourseOnlineStudentDTOResponse> list = courseOnlineStudentService.getHistory();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> createCourseOnlineStudent(@RequestBody CreateCourseOnlineStudent model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        CourseOnlineStudentDTO courseOnlineStudentDTO = courseOnlineStudentService.buyCourse(model, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineStudentDTO)
        );
    }

    @GetMapping("/check/{slug}")
    ResponseEntity<ResponseObject> checkSubCourse(
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        boolean checked = courseOnlineStudentService.checkCourseOnlineRegistered(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", checked)
        );
    }
}
