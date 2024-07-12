package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online_student.TestOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;
import com.englishacademy.EnglishAcademy.models.test_input.CreateTestInput;
import com.englishacademy.EnglishAcademy.models.test_online.CreateTestOnline;
import com.englishacademy.EnglishAcademy.models.test_online.EditTestOnline;
import com.englishacademy.EnglishAcademy.services.TestOnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-online")
public class TestOnlineController {
    private final TestOnlineService testOnlineService;

    public TestOnlineController(TestOnlineService testOnlineService) {
        this.testOnlineService = testOnlineService;
    }

    @GetMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailTest(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        TestOnlineDetail testInputDetail = testOnlineService.getdetailTest(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testInputDetail)
        );
    }

    @PostMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> submitTest(
            @RequestBody SubmitTest submitTest,
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        String code = testOnlineService.submitTest(slug, currentStudent.getId(), submitTest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", code)
        );
    }

    @GetMapping("/result/{code}")
    ResponseEntity<ResponseObject> getResultTest(@PathVariable("code") String code) {
        TestOnlineStudentDTO testOnlineStudentDTO = testOnlineService.getresultTest(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOnlineStudentDTO)
        );
    }

    @PostMapping("/create")
    ResponseEntity<ResponseObject> insert(
            @RequestParam("title") String title,
            @RequestParam("time") Integer time,
            @RequestParam("pastMark") Integer pastMark,
            @RequestParam("totalMark") Integer totalMark,
            @RequestParam("description") String description,
            @RequestParam("topicId") Long topicId,
            @RequestParam("file") MultipartFile file)
    {
        CreateTestOnline createTestOnline = new CreateTestOnline();
        createTestOnline.setTitle(title);
        createTestOnline.setFile(file);
        createTestOnline.setPastMark(pastMark);
        createTestOnline.setTotalMark(totalMark);
        createTestOnline.setTime(time);
        createTestOnline.setTopicId(topicId);
        createTestOnline.setDescription(description);
        testOnlineService.saveTestOnline(createTestOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PutMapping("/edit")
    ResponseEntity<ResponseObject> edit(@RequestBody EditTestOnline editTestOnline){
        TestOnlineDTO testOnlineDTO = testOnlineService.edit(editTestOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", testOnlineDTO)
        );
    }

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> edit(@RequestBody List<Long> ids){
        testOnlineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
