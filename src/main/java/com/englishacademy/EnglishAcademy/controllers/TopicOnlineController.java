package com.englishacademy.EnglishAcademy.controllers;


import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.services.ITopicOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic-online")
public class TopicOnlineController {
    @Autowired
    private ITopicOnlineService topicOnlineService;

    @GetMapping("/{slug}/{userId}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug, @PathVariable("userId") Long userId) {
        try {
            List<TopicOnlineDetail> list = topicOnlineService.findAllByCourseSlug(slug, userId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", list)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }
}
