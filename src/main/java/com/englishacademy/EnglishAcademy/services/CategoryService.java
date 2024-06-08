package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.category.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
}
