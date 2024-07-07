package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.course_online.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.course_online.EditCourseOnline;
import com.englishacademy.EnglishAcademy.services.CourseOnlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/any")
public class CourseOnlineController {
    @Autowired
    private CourseOnlineService courseOnlineService;

    @GetMapping("/course-online")
    //@PreAuthorize("hasAnyAuthority('STUDENT')")
    ResponseEntity<ResponseObject> getAll() {
        List<CourseOnlineDTO> list = courseOnlineService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-online/get-top")
    ResponseEntity<ResponseObject> getCourseOnlineTop() {
        List<CourseOnlineDTO> list = courseOnlineService.getCourseTop6();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-online/toeic/get-score/{score}")
    ResponseEntity<ResponseObject> getCourseOnlineTop(@PathVariable("score") Integer score) {
        List<CourseOnlineDTO> list = courseOnlineService.getCourseTopToeic(score);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-online/ielts/get-score/{score}")
    ResponseEntity<ResponseObject> getCourseOnlineTopIelts(@PathVariable("score") Integer score) {
        List<CourseOnlineDTO> list = courseOnlineService.getCourseTopIelts(score);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-online/by-student")
    ResponseEntity<ResponseObject> getAllByStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        List<CourseOnlineResponse> list = courseOnlineService.findAllByStudent(currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-online/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        CourseOnlineDTO courseOnlineDTO = courseOnlineService.findBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDTO)
        );
    }

    @PostMapping("/course-online")
    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> createCourseOnline(@Valid @RequestBody CreateCourseOnline model) {
        CourseOnlineDTO courseOnlineDTO = courseOnlineService.create(model);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDTO)
        );
    }

    @PutMapping("/course-online")
    ResponseEntity<ResponseObject> editCourseOnline(@Valid @RequestBody EditCourseOnline model) {
        CourseOnlineDTO courseOnlineDTO = courseOnlineService.edit(model);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDTO)
        );
    }

    @DeleteMapping("/course-online")
    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deleteCourseOnline(@RequestBody Long[] ids) {
        courseOnlineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("course-online/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        CourseOnlineDetail courseOnlineDetail = courseOnlineService.getDetail(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDetail)
        );
    }
}
