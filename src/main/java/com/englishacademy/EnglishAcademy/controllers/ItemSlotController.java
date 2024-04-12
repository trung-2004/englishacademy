package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.services.IItemSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item-slot")
public class ItemSlotController {
    @Autowired
    private IItemSlotService itemSlotService;

    @GetMapping("/{slug}/{studentId}")
    ResponseEntity<ResponseObject> getDetail(
            @PathVariable("slug") String slug,
            @PathVariable("studentId") Long studentId
    ){
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetail(slug, studentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }
}
