package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.answerStudentItemSlot.ListScore;
import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.AnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.models.answerStudent.CreateAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.models.answerStudent.ScoreAnswerStudentItemSlot;
import com.englishacademy.EnglishAcademy.services.IAnswerStudentItemSlotService;
import com.englishacademy.EnglishAcademy.services.IItemSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answer-student-item-slot")
@RequiredArgsConstructor
public class AnswerStudentItemSlotController {
    private final IAnswerStudentItemSlotService answerStudentItemSlotService;
    private final IItemSlotService itemSlotService;
    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("")
    ResponseEntity<ResponseObject> createAnswer(
            @RequestBody CreateAnswerStudentItemSlot answerStudentItemSlot
            ){
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
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
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
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
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        Student currentStudent = (Student) auth.getPrincipal();
        ListScore listScore = answerStudentItemSlotService.listScore(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", listScore)
        );
    }

}
