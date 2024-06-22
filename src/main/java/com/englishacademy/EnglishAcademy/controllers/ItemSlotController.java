package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDTO;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.item_slot.CreateItemSlot;
import com.englishacademy.EnglishAcademy.models.item_slot.EditItemSlot;
import com.englishacademy.EnglishAcademy.services.ItemSlotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@RestController
//@RequestMapping("/api/v1/item-slot")
public class ItemSlotController extends TextWebSocketHandler {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private ItemSlotService itemSlotService;

    @MessageMapping("/{slug}")
    public ResponseEntity<ResponseObject> getDetailWeb(@PathVariable("slug") String slug){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetail(slug, 1L);
        System.out.println(itemSlotDetail.getId());
        this.template.convertAndSend("/topic/"+ slug, itemSlotDetail);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }

    /*@MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ItemSlotDetail receivePublicMessage(@Payload ItemSlotDetail itemSlotDetail){
        System.out.println(itemSlotDetail.toString());
        return itemSlotDetail;
    }*/

    @GetMapping("/api/v1/item-slot/{slug}")
    public ResponseEntity<ResponseObject> getDetail(@PathVariable("slug") String slug){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) auth.getPrincipal();
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetail(slug, currentStudent.getId());
        this.template.convertAndSend("/topic/"+ slug, itemSlotDetail);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }

    @GetMapping("/api/v1/item-slot/user/{slug}/{classId}")
    public ResponseEntity<ResponseObject> getDetailByUser(@PathVariable("slug") String slug, @PathVariable("classId") Long classId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetailByUser(slug, currentUser.getId(), classId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/api/v1/item-slot/get-by-slug/{slug}")
    public ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        ItemSlotDTO itemSlotDetail = itemSlotService.getBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/api/v1/item-slot")
    public ResponseEntity<ResponseObject> create(@RequestBody @Valid CreateItemSlot createItemSlot){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        ItemSlotDTO itemSlotDetail = itemSlotService.create(createItemSlot, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/api/v1/item-slot")
    public ResponseEntity<ResponseObject> edit(@RequestBody @Valid EditItemSlot editItemSlot){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        ItemSlotDTO itemSlotDetail = itemSlotService.edit(editItemSlot, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/api/v1/item-slot")
    public ResponseEntity<ResponseObject> getBySlug(@RequestBody Long[] ids){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        itemSlotService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
}
