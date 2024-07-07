package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.admin.Menu;
import com.englishacademy.EnglishAcademy.dtos.admin.MenuItem;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("menu")
    public ResponseEntity<ResponseObject> getMenu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<Menu> menuItems = adminService.getMenu(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

}
