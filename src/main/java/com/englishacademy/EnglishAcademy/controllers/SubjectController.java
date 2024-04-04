package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.services.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    @GetMapping("/detail/{slug}/{studentId}")
    ResponseEntity<ResponseObject> getDetailSubjectBySlug(
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ) {
        SubjectDetail subjectDetail = subjectService.getDetail(slug, studentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", subjectDetail)
        );
    }
}
