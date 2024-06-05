package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.packages.PackageDTO;
import com.englishacademy.EnglishAcademy.services.IPackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PackageController {
    private final IPackageService packageService;

    public PackageController(IPackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("/any/package/by-tutor/{code}")
    ResponseEntity<ResponseObject> getAllByTutor(@PathVariable("code") String code) {
        List<PackageDTO> list = packageService.findAllByTutor(code);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }
}
