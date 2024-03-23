package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;

public interface IItemOnlineService {
    ItemOnlineDetail getItemOnlineDetail(String slug);

    ItemOnlineDTO completeItem(String slug, Long id);
}
