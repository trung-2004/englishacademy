package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDetail;

public interface IItemSlotService {
    ItemSlotDetail getDetail(String slug, Long studentId);
}
