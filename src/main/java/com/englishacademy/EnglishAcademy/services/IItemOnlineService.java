package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;

public interface IItemOnlineService {
    ItemOnlineDetail getItemOnlineDetail(String slug);

    void completeItem(String slug, Long id);
}
