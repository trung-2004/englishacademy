package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answerStudent.ScoreAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.services.IAnswerStudentItemSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/answer-student-item-slot")
public class AnswerStudentItemSlotController {
    private final IAnswerStudentItemSlotService answerStudentItemSlotService;
    public AnswerStudentItemSlotController(IAnswerStudentItemSlotService answerStudentItemSlotService) {
        this.answerStudentItemSlotService = answerStudentItemSlotService;
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> createAnswer(
            @RequestBody CreateAnswerStudentItemSlot answerStudentItemSlot
            ){
        answerStudentItemSlotService.save(answerStudentItemSlot, 1L);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PutMapping("/score")
    ResponseEntity<ResponseObject> scoreAnswer(
            @RequestBody ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot
    ){
        answerStudentItemSlotService.scoreAnswer(scoreAnswerStudentItemSlot, 1L);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
