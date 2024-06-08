package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDetail;

public interface ItemSlotService {
    ItemSlotDetail getDetail(String slug, Long studentId);
}
