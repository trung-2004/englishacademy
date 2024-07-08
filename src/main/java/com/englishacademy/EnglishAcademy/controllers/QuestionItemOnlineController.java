package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.question_item_online.QuestionItemOnlineDTO;
import com.englishacademy.EnglishAcademy.models.item_online.CreateItemOnline;
import com.englishacademy.EnglishAcademy.models.item_online.EditItemOnline;
import com.englishacademy.EnglishAcademy.models.question_item_online.CreateQuestionItemOnline;
import com.englishacademy.EnglishAcademy.models.question_item_online.EditQuestionItemOnline;
import com.englishacademy.EnglishAcademy.services.QuestionItemOnlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/question-item-online")
@RequiredArgsConstructor
public class QuestionItemOnlineController {
    private final QuestionItemOnlineService questionItemOnlineService;


    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("id") Long id) {
        QuestionItemOnlineDTO questionItemOnlineDTO = questionItemOnlineService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", questionItemOnlineDTO)
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> create(@RequestBody @Valid CreateQuestionItemOnline createQuestionItemOnline) {
        QuestionItemOnlineDTO questionItemOnlineDTO = questionItemOnlineService.create(createQuestionItemOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", questionItemOnlineDTO)
        );
    }

    @PutMapping("")
    ResponseEntity<ResponseObject> edit(@RequestBody @Valid EditQuestionItemOnline editQuestionItemOnline) {
        QuestionItemOnlineDTO questionItemOnlineDTO = questionItemOnlineService.edit(editQuestionItemOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", questionItemOnlineDTO)
        );
    }

    @DeleteMapping("/item-online")
    ResponseEntity<ResponseObject> delete(@RequestBody Long[] ids) {
        questionItemOnlineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
