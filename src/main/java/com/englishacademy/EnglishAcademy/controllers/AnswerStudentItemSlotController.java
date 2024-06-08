package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot.ListScore;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.answer_student.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answer_student.ScoreAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.services.AnswerStudentItemSlotService;
import com.englishacademy.EnglishAcademy.services.ItemSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/answer-student-item-slot")
@RequiredArgsConstructor
public class AnswerStudentItemSlotController {
    private final AnswerStudentItemSlotService answerStudentItemSlotService;
    private final ItemSlotService itemSlotService;
    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("")
    ResponseEntity<ResponseObject> createAnswer(
            @RequestBody CreateAnswerStudentItemSlot answerStudentItemSlot
            ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        AnswerStudentItemSlot answerStudentItemSlot1 = answerStudentItemSlotService.save(answerStudentItemSlot, currentStudent.getId());

        // Get the updated itemSlotDetail
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetail(answerStudentItemSlot1.getItemSlot().getSlug(), currentStudent.getId());

        // Send the updated itemSlotDetail to the client
        this.template.convertAndSend("/topic/" + answerStudentItemSlot1.getItemSlot().getSlug(), itemSlotDetail);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PutMapping("/score")
    ResponseEntity<ResponseObject> scoreAnswer(
            @RequestBody ScoreAnswerStudentItemSlot scoreAnswerStudentItemSlot
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        AnswerStudentItemSlot answerStudentItemSlot = answerStudentItemSlotService.scoreAnswer(scoreAnswerStudentItemSlot, currentStudent.getId());

        // Get the updated itemSlotDetail
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetail(answerStudentItemSlot.getItemSlot().getSlug(), currentStudent.getId());

        // Send the updated itemSlotDetail to the client
        this.template.convertAndSend("/topic/" + answerStudentItemSlot.getItemSlot().getSlug(), itemSlotDetail);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("/list-score/{slug}")
    ResponseEntity<ResponseObject> listScoreAnswer(@PathVariable("slug") String slug){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        ListScore listScore = answerStudentItemSlotService.listScore(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", listScore)
        );
    }

}
