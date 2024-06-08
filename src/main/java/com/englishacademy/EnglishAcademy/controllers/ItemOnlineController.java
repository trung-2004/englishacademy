package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.services.ItemOnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-online")
public class ItemOnlineController {
    private final ItemOnlineService itemOnlineService;

    public ItemOnlineController(ItemOnlineService itemOnlineService) {
        this.itemOnlineService = itemOnlineService;
    }

    @GetMapping("/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        ItemOnlineDetail itemOnlineDetail = itemOnlineService.getItemOnlineDetail(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemOnlineDetail)
        );
    }

    @PutMapping("/{slug}")
    ResponseEntity<ResponseObject> completeItem(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        itemOnlineService.completeItem(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
