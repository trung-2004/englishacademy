package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.category.CategoryDTO;
import com.englishacademy.EnglishAcademy.entities.Category;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CategoryMapper;
import com.englishacademy.EnglishAcademy.models.category.CreateCategory;
import com.englishacademy.EnglishAcademy.models.category.EditCategory;
import com.englishacademy.EnglishAcademy.repositories.CategoryRepository;
import com.englishacademy.EnglishAcademy.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug);
        if (category == null) throw new AppException(ErrorCode.CATEGORY_NOTFOUND);
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO create(CreateCategory createCategory, User user) {
        Category categoryExisting = categoryRepository.findBySlug(createCategory.getName().toLowerCase().replace(" ", "-"));
        if (categoryExisting != null) throw new AppException(ErrorCode.CATEGORY_EXISTED);
        Category category = Category.builder()
                .name(createCategory.getName())
                .slug(createCategory.getName().toLowerCase().replace(" ", "-"))
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();
        categoryRepository.save(category);
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO update(EditCategory editCategory, User user) {
        Category categoryExisting = categoryRepository.findById(editCategory.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        Category categoryExisting1 = categoryRepository.findBySlug(editCategory.getName().toLowerCase().replace(" ", "-"));
        if (categoryExisting1 != null) throw new AppException(ErrorCode.CATEGORY_EXISTED);

        categoryExisting.setName(editCategory.getName());
        categoryExisting.setSlug(editCategory.getName().toLowerCase().replace(" ", "-"));
        categoryRepository.save(categoryExisting);
        return categoryMapper.toCategoryDTO(categoryExisting);
    }

    @Override
    public void delete(Long[] ids) {
        categoryRepository.deleteAllById(List.of(ids));
    }
}
