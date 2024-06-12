package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.models.item_online.CreateItemOnline;
import com.englishacademy.EnglishAcademy.models.item_online.EditItemOnline;

public interface ItemOnlineService {
    ItemOnlineDetail getItemOnlineDetail(String slug);

    void completeItem(String slug, Long id);

    ItemOnlineDTO findBySlug(String slug);

    ItemOnlineDTO create(CreateItemOnline createItemOnline);

    ItemOnlineDTO edit(EditItemOnline editItemOnline);

    void delete(Long[] ids);
}
