package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.models.item_online.CreateItemOnline;
import com.englishacademy.EnglishAcademy.models.item_online.EditItemOnline;
import com.englishacademy.EnglishAcademy.services.ItemOnlineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ItemOnlineController {
    private final ItemOnlineService itemOnlineService;

    public ItemOnlineController(ItemOnlineService itemOnlineService) {
        this.itemOnlineService = itemOnlineService;
    }

    @GetMapping("/item-online/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        ItemOnlineDetail itemOnlineDetail = itemOnlineService.getItemOnlineDetail(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemOnlineDetail)
        );
    }

    @PutMapping("/item-online/{slug}")
    ResponseEntity<ResponseObject> completeItem(@PathVariable("slug") String slug) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        itemOnlineService.completeItem(slug, currentStudent.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @GetMapping("/any/item-online/get-by-slug/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        ItemOnlineDTOResponse itemOnlineDTO = itemOnlineService.findBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemOnlineDTO)
        );
    }

    @PostMapping("/item-online")
    ResponseEntity<ResponseObject> getBySlug(@RequestBody @Valid CreateItemOnline createItemOnline) {
        ItemOnlineDTO itemOnlineDTO = itemOnlineService.create(createItemOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemOnlineDTO)
        );
    }

    @PutMapping("/item-online")
    ResponseEntity<ResponseObject> getBySlug(@RequestBody @Valid EditItemOnline editItemOnline) {
        ItemOnlineDTO itemOnlineDTO = itemOnlineService.edit(editItemOnline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemOnlineDTO)
        );
    }

    @DeleteMapping("/item-online")
    ResponseEntity<ResponseObject> getBySlug(@RequestBody Long[] ids) {
        itemOnlineService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
