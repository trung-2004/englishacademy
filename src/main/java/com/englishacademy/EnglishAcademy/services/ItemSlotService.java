package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDTO;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.item_slot.CreateItemSlot;
import com.englishacademy.EnglishAcademy.models.item_slot.EditItemSlot;

public interface ItemSlotService {
    ItemSlotDetail getDetail(String slug, Long studentId);
    ItemSlotDetail getDetailByUser(String slug, Long id, Long classId);
    ItemSlotDTO getBySlug(String slug);
    ItemSlotDTO create(CreateItemSlot createItemSlot, User currentUser);
    ItemSlotDTO edit(EditItemSlot editItemSlot, User currentUser);
    void delete(Long[] ids);
}
