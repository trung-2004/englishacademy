package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;

public interface ItemOnlineService {
    ItemOnlineDetail getItemOnlineDetail(String slug);

    void completeItem(String slug, Long id);
}
