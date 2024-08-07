package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.question_test_input.QuestionTestInputDetailResult;
import com.englishacademy.EnglishAcademy.dtos.test_input.TestInputDTO;
import com.englishacademy.EnglishAcademy.dtos.test_input.TestInputDetail;
import com.englishacademy.EnglishAcademy.dtos.test_input_student.TestInputStudentDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.answer_student.SubmitTest;
import com.englishacademy.EnglishAcademy.models.test_input.CreateTestInput;
import com.englishacademy.EnglishAcademy.models.test_input.EditTestInput;
import com.englishacademy.EnglishAcademy.services.TestInputService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TestInputController {
    private final TestInputService testInputService;

    public TestInputController(TestInputService testInputService) {
        this.testInputService = testInputService;
    }

    @GetMapping("/any/test-input")
    ResponseEntity<ResponseObject> getAllToiec() {
        List<TestInputDTO> list = testInputService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", list)
        );
    }


    @GetMapping("/any/detail/{slug}")
    ResponseEntity<ResponseObject> getDetailTest(@PathVariable("slug") String slug) {
        TestInputDetail testInputDetail = testInputService.getdetailTest(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", testInputDetail)
        );
    }

    @PostMapping("/detail/{slug}")
    ResponseEntity<ResponseObject> submitTest(
            @RequestBody SubmitTest submitTest,
            @PathVariable("slug") String slug
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        String code = testInputService.submitTest(slug, currentStudent.getId(), submitTest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", code)
        );
    }

    @GetMapping("/result/{code}")
    ResponseEntity<ResponseObject> getResultTest(@PathVariable("code") String code) {
        TestInputStudentDTO testInputStudentDTO = testInputService.getresultTest(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", testInputStudentDTO)
        );
    }

    @GetMapping("/result-detail/{code}")
    ResponseEntity<ResponseObject> getResultDetailTest(@PathVariable("code") String code) {
        List<QuestionTestInputDetailResult> questionTestInputDetailResults = testInputService.getresultDetailTest(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", questionTestInputDetailResults)
        );
    }

    @PostMapping("/test-input")
    ResponseEntity<ResponseObject> insert(@RequestParam("title") String title,
                                          @RequestParam("type") Integer type,
                                          @RequestParam("time") Integer time,
                                          @RequestParam("description") String description,
                                          @RequestParam("file") MultipartFile file) {
            CreateTestInput createTestInput = new CreateTestInput();
            createTestInput.setTitle(title);
            createTestInput.setFile(file);
            createTestInput.setTime(time);
            createTestInput.setType(type);
            createTestInput.setDescription(description);

            testInputService.saveTestInput(createTestInput);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "Successfully", "")
            );
    }

    @GetMapping("/test-input/{slug}")
    ResponseEntity<ResponseObject> getTestInput(@PathVariable("slug") String slug) {
        TestInputDTO testInputDTO = testInputService.getBySLug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", testInputDTO)
        );
    }

    @PutMapping("/test-input")
    ResponseEntity<ResponseObject> update(@RequestBody EditTestInput editTestInput) {
        TestInputDTO testInputDTO = testInputService.edit(editTestInput);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", testInputDTO)
        );
    }

    @DeleteMapping("/test-input")
    ResponseEntity<ResponseObject> delete(@RequestBody List<Long> ids) {
        testInputService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully", "")
        );
    }

}
