package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.services.IItemOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-online")
public class ItemOnlineController {
    @Autowired
    private IItemOnlineService itemOnlineService;

    @GetMapping("/{slug}")
    ResponseEntity<ResponseObject> getDetailSlug(@PathVariable("slug") String slug) {
        try {
            ItemOnlineDetail itemOnlineDetail = itemOnlineService.getItemOnlineDetail(slug);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", itemOnlineDetail)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }

    @PutMapping("/{slug}/{userId}")
    ResponseEntity<ResponseObject> completeItem(@PathVariable("slug") String slug, @PathVariable("userId") Long userId) {
        try {
            ItemOnlineDTO itemOnlineDTO = itemOnlineService.completeItem(slug, userId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, "ok", itemOnlineDTO)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(false, e.getMessage(), "")
            );
        }
    }
}
