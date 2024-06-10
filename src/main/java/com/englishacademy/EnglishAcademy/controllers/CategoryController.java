package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.category.CategoryDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.category.CreateCategory;
import com.englishacademy.EnglishAcademy.models.category.EditCategory;
import com.englishacademy.EnglishAcademy.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/any/category")
    ResponseEntity<ResponseObject> getAll() {
        List<CategoryDTO> list = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
    @GetMapping("/category/{slug}")
    ResponseEntity<ResponseObject> getBySlug(@PathVariable("slug") String slug) {
        CategoryDTO categoryDTO = categoryService.findBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", categoryDTO)
        );
    }
    @PostMapping("/category")
    ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateCategory createCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CategoryDTO categoryDTO = categoryService.create(createCategory, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", categoryDTO)
        );
    }
    @PutMapping("/category")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody EditCategory editCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CategoryDTO categoryDTO = categoryService.update(editCategory, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Update Success", categoryDTO)
        );
    }

    @DeleteMapping("/category")
    ResponseEntity<ResponseObject> update(@RequestBody Long[] ids) {
        categoryService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete success", "")
        );
    }

}
