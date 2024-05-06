package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.services.IItemSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Controller
//@RequestMapping("/api/v1/item-slot")
public class ItemSlotController extends TextWebSocketHandler {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private IItemSlotService itemSlotService;

    @MessageMapping("/{slug}")
    public ResponseEntity<ResponseObject> getDetailWeb(@PathVariable("slug") String slug){
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
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
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof Student)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        Student currentStudent = (Student) auth.getPrincipal();
        ItemSlotDetail itemSlotDetail = itemSlotService.getDetail(slug, currentStudent.getId());
        this.template.convertAndSend("/topic/"+ slug, itemSlotDetail);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", itemSlotDetail)
        );
    }
}
