package com.englishacademy.EnglishAcademy.controllers;


import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.topic_online.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.services.TopicOnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topic-online")
public class TopicOnlineController {
    private final TopicOnlineService topicOnlineService;

    public TopicOnlineController(TopicOnlineService topicOnlineService) {
        this.topicOnlineService = topicOnlineService;
    }

    @GetMapping("/{slug}/{userId}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        CourseOnlineTopicDetailResponse list = topicOnlineService.findAllByCourseSlug(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
}
