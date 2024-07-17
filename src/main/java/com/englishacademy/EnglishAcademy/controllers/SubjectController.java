package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.subject.CreateSubject;
import com.englishacademy.EnglishAcademy.models.subject.EditSubject;
import com.englishacademy.EnglishAcademy.services.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;


    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailSubjectBySlug(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        SubjectDetail subjectDetail = subjectService.getDetail(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDetail)
        );
    }

    @GetMapping("/detail/user/{slug}/{classId}")
    ResponseEntity<ResponseObject> getDetailSubjectBySlugUser(@PathVariable("slug") String slug, @PathVariable("classId") Long classId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentStudent = (User) auth.getPrincipal();
        SubjectDetail subjectDetail = subjectService.getDetailByUser(slug, currentStudent.getId(), classId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDetail)
        );
    }

    @GetMapping("/get-all/{slug}")
    ResponseEntity<ResponseObject> getAllByCourse(@PathVariable("slug") String slug) {
        List<SubjectDTO> subjectDTOs = subjectService.getAllByCourseSlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDTOs)
        );
    }

    @GetMapping("/get-all-score/{slug}")
    ResponseEntity<ResponseObject> getAllScoreByCourse(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        List<SubjectDTOResponse> subjectDTOs = subjectService.getAllScoreByCourseSlug(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDTOs)
        );
    }

    @GetMapping("/{slug}")
    ResponseEntity<ResponseObject> getById(@PathVariable("slug") String slug) {
        SubjectDTO subjectDTO = subjectService.getBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDTO)
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> create(@RequestBody @Valid CreateSubject createSubject) {
        SubjectDTO subjectDTO = subjectService.create(createSubject);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDTO)
        );
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> edit(@RequestBody @Valid EditSubject editSubject) {
        SubjectDTO subjectDTO = subjectService.edit(editSubject);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDTO)
        );
    }

    @DeleteMapping("")
    ResponseEntity<ResponseObject> delete(@RequestBody @Valid Long[] ids) {
        subjectService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
