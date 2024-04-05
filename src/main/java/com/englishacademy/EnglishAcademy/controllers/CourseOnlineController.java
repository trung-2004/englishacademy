package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.models.courseOnline.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.courseOnline.EditCourseOnline;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-online")
public class CourseOnlineController {
    @Autowired
    private ICourseOnlineService courseOnlineService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAll() {
        try {
            List<CourseOnlineDTO> list = courseOnlineService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", list)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, 404, e.getMessage(), "")
            );
        }
    }

    @GetMapping("/student/{studentId}")
    ResponseEntity<ResponseObject> getAllByStudent(@PathVariable("studentId") Long studentId) {
        try {
            List<CourseOnlineResponse> list = courseOnlineService.findAllByStudent(studentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", list)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, 404, e.getMessage(), "")
            );
        }
    }

    @GetMapping("/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        try {
            CourseOnlineDTO courseOnlineDTO = courseOnlineService.findBySlug(slug);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", courseOnlineDTO)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, 400, "Can't not find product with slug: "+slug, "")
            );
        }
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> createCourseOnline(@ModelAttribute CreateCourseOnline model) {
        /*try {
            CourseOnlineDTO courseOnlineDTO = courseOnlineService.create(model);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", courseOnlineDTO)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, e., e.getMessage(), "")
            );
        }*/
        CourseOnlineDTO courseOnlineDTO = courseOnlineService.create(model);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", courseOnlineDTO)
        );
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> editCourseOnline(@ModelAttribute EditCourseOnline model) {
        try {
            CourseOnlineDTO courseOnlineDTO = courseOnlineService.edit(model);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", courseOnlineDTO)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, 400, e.getMessage(), "")
            );
        }
    }

    @DeleteMapping("")
    ResponseEntity<ResponseObject> deleteCourseOnline(@RequestBody Long[] ids) {
        try {
            courseOnlineService.delete(ids);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", "")
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(false, 400, e.getMessage(), "")
            );
        }
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        try {
            CourseOnlineDetail courseOnlineDetail = courseOnlineService.getDetail(slug);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", courseOnlineDetail)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, 400, "Can't not find product with slug: "+slug, "")
            );
        }
    }
}
