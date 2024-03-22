package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.mappers.ItemOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineRepository;
import com.englishacademy.EnglishAcademy.services.IItemOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemOnlineService implements IItemOnlineService {
    @Autowired
    private ItemOnlineRepository itemOnlineRepository;
    @Autowired
    private ItemOnlineMapper itemOnlineMapper;

    @Override
    public ItemOnlineDetail getItemOnlineDetail(String slug) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        return itemOnlineMapper.toItemOnlineDetail(model);
    }
}
