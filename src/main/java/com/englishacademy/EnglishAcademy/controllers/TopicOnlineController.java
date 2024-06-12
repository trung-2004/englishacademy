package com.englishacademy.EnglishAcademy.controllers;


import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.topic_online.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.topic_online.CreateTopicOnline;
import com.englishacademy.EnglishAcademy.models.topic_online.EditTopicOnline;
import com.englishacademy.EnglishAcademy.services.TopicOnlineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TopicOnlineController {
    private final TopicOnlineService topicOnlineService;

    public TopicOnlineController(TopicOnlineService topicOnlineService) {
        this.topicOnlineService = topicOnlineService;
    }

    @GetMapping("/topic-online/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        CourseOnlineTopicDetailResponse list = topicOnlineService.findAllByCourseSlug(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/any/topic-online/get-by-slug/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        TopicOnlineDTO list = topicOnlineService.findBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("/any/topic-online")
    ResponseEntity<ResponseObject> create(@RequestBody @Valid CreateTopicOnline createTopicOnline) {
        TopicOnlineDTO list = topicOnlineService.create(createTopicOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PutMapping("/any/topic-online")
    ResponseEntity<ResponseObject> edit(@RequestBody @Valid EditTopicOnline editTopicOnline) {
        TopicOnlineDTO list = topicOnlineService.edit(editTopicOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @DeleteMapping("/any/topic-online")
    ResponseEntity<ResponseObject> delete(@RequestBody Long[] ids) {
        topicOnlineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

}
