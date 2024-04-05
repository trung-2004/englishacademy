package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.category.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> findAll();
}
