package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.classes.CLassesDTO;
import com.englishacademy.EnglishAcademy.dtos.classes.RoomDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.classes.CreateClasses;
import com.englishacademy.EnglishAcademy.services.ClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
public class ClassesController {
    private final ClassesService classesService;

    @GetMapping("/by-teacher")
    ResponseEntity<ResponseObject> getAllClassByTeacher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<CLassesDTO> list = classesService.getAllByTeacher(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/get-all")
    ResponseEntity<ResponseObject> getAllClass() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<CLassesDTO> list = classesService.getAll(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/by-teacher/count")
    ResponseEntity<ResponseObject> getCountAllClassByTeacher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        int totalClasses = classesService.countClassesByTeacher(currentUser);
        Map<String, Integer> result = new HashMap<>();
        result.put("totalClasses", totalClasses);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", result)
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> create(@RequestBody CreateClasses createClasses) {
        CLassesDTO list = classesService.create(createClasses);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/get-all/room")
    ResponseEntity<ResponseObject> getAllClassRoom() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<RoomDTO> list = classesService.getAllRoom(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
}
