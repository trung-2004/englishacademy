package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.courseOnline.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.courseOnline.EditCourseOnline;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseOnlineController {
    @Autowired
    private ICourseOnlineService courseOnlineService;

    @GetMapping("/any/course-online")
    //@PreAuthorize("hasAnyAuthority('STUDENT')")
    ResponseEntity<ResponseObject> getAll() {
        List<CourseOnlineDTO> list = courseOnlineService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/course-online/by-student")
    ResponseEntity<ResponseObject> getAllByStudent() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> createCourseOnline(@ModelAttribute CreateCourseOnline model) {
        CourseOnlineDTO courseOnlineDTO = courseOnlineService.create(model);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDTO)
        );
    }

    @PutMapping("/course-online")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> editCourseOnline(@ModelAttribute EditCourseOnline model) {
        CourseOnlineDTO courseOnlineDTO = courseOnlineService.edit(model);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDTO)
        );
    }

    @DeleteMapping("/course-online")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deleteCourseOnline(@RequestBody Long[] ids) {
        courseOnlineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("/any/course-online/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        CourseOnlineDetail courseOnlineDetail = courseOnlineService.getDetail(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDetail)
        );
    }
}
