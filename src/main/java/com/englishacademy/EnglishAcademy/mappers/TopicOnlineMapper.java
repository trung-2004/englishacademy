package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicOnlineMapper {
    @Autowired
    private ItemOnlineMapper itemOnlineMapper;
    public TopicOnlineDTO toTopicOnlineDTO(TopicOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        TopicOnlineDTO topicOnlineDTO = TopicOnlineDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return topicOnlineDTO;
    }

    public TopicOnlineDetail toTopicOnlineDetail(TopicOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        List<ItemOnlineDTO> itemOnlineDTOS = model.getItemOnlines().stream().map(itemOnlineMapper::toItemOnlineDTO).collect(Collectors.toList());



        itemOnlineDTOS.sort(Comparator.comparingInt(ItemOnlineDTO::getOrderTop));

        TopicOnlineDetail topicOnlineDetail = TopicOnlineDetail.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .createdBy(model.getCreatedBy())
                .itemOnlineDTOList(itemOnlineDTOS)
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return topicOnlineDetail;
    }

    public TopicOnlineDetail toTopicOnlineAndStudentDetail(TopicOnline model, Student student){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        List<ItemOnlineDTO> itemOnlineDTOS = new ArrayList<>();
        for (ItemOnline itemOnline: model.getItemOnlines()) {
            ItemOnlineDTO itemOnlineDTO = itemOnlineMapper.toItemOnlineStudentDTO(itemOnline, student);
            itemOnlineDTOS.add(itemOnlineDTO);
        }


        itemOnlineDTOS.sort(Comparator.comparingInt(ItemOnlineDTO::getOrderTop));

        TopicOnlineDetail topicOnlineDetail = TopicOnlineDetail.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .createdBy(model.getCreatedBy())
                .itemOnlineDTOList(itemOnlineDTOS)
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return topicOnlineDetail;
    }
}
