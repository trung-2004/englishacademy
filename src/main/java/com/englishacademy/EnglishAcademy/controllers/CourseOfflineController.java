package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.course_offline.CreateCourseOffline;
import com.englishacademy.EnglishAcademy.models.course_offline.EditCourseOffline;
import com.englishacademy.EnglishAcademy.services.CourseOfflineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseOfflineController {
    @Autowired
    private CourseOfflineService courseOfflineService;
    @GetMapping("/course-offline")
    ResponseEntity<ResponseObject> getAll() {
        List<CourseOfflineDTO> list = courseOfflineService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-offline/get-by-class")
    ResponseEntity<ResponseObject> getAllCourseByClass() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        List<CourseOfflineDTO> list = courseOfflineService.findByStudent(currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-offline/detail/{slug}")
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

    @GetMapping("/course-offline/user/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailCourseByClassTeacher(
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CourseOfflineDetail courseOfflineDetail = courseOfflineService.getDetailTeacher(slug, currentUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOfflineDetail)
        );
    }

    @GetMapping("/course-offline/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        CourseOfflineDTO courseOfflineDTO = courseOfflineService.findBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOfflineDTO)
        );
    }

    @PostMapping("/course-offline")
        //@PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> createCourseOffline(@Valid @RequestBody CreateCourseOffline model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CourseOfflineDTO courseOfflineDTO = courseOfflineService.create(model, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOfflineDTO)
        );
    }

    @PutMapping("/course-offline")
    ResponseEntity<ResponseObject> editCourseOffline(@Valid @RequestBody EditCourseOffline model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CourseOfflineDTO courseOfflineDTO = courseOfflineService.edit(model, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOfflineDTO)
        );
    }

    @DeleteMapping("/course-offline")
        //@PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deleteCourseOffline(@RequestBody Long[] ids) {
        courseOfflineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
