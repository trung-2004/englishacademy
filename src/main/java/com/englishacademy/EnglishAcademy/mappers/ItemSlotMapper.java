package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDTO;
import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotResponse;
import com.englishacademy.EnglishAcademy.entities.ItemSlot;
import org.springframework.stereotype.Component;

@Component
public class ItemSlotMapper {
    public ItemSlotDTO toItemSlotDTO(ItemSlot model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        ItemSlotDTO itemSlotDTO = ItemSlotDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .pathUrl(model.getPathUrl())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return itemSlotDTO;
    }

    public ItemSlotResponse toItemSlotResponse(ItemSlot model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        ItemSlotResponse itemSlotResponse = ItemSlotResponse.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .itemType(model.getItemType())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return itemSlotResponse;
    }
}
