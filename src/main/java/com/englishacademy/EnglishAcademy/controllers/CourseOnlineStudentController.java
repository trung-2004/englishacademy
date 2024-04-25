package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnlineStudent.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.courseOnlineStudent.CreateCourseOnlineStudent;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-online-student")
public class CourseOnlineStudentController {
    @Autowired
    private ICourseOnlineStudentService courseOnlineStudentService;

    @PostMapping("")
    ResponseEntity<ResponseObject> createCourseOnlineStudent(@RequestBody CreateCourseOnlineStudent model) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
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
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        Student currentStudent = (Student) auth.getPrincipal();
        boolean checked = courseOnlineStudentService.checkCourseOnlineRegistered(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", checked)
        );
    }
}
