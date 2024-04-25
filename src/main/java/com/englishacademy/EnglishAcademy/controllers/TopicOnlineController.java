package com.englishacademy.EnglishAcademy.controllers;


import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.services.ITopicOnlineService;
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
@RequestMapping("/api/v1/topic-online")
public class TopicOnlineController {
    private final ITopicOnlineService topicOnlineService;

    public TopicOnlineController(ITopicOnlineService topicOnlineService) {
        this.topicOnlineService = topicOnlineService;
    }

    @GetMapping("/{slug}/{userId}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        Student currentStudent = (Student) auth.getPrincipal();
        CourseOnlineTopicDetailResponse list = topicOnlineService.findAllByCourseSlug(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
}
