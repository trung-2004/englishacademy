package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.category.CategoryDTO;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.category.CreateCategory;
import com.englishacademy.EnglishAcademy.models.category.EditCategory;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
    CategoryDTO findBySlug(String slug);
    CategoryDTO create(CreateCategory createCategory, User user);
    CategoryDTO update(EditCategory editCategory, User user);
    void delete(Long[] ids);
}
