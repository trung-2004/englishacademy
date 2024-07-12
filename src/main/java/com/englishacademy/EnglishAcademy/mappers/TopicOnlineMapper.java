package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.test_online.TestOnlineResponseDTO;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.TestOnlineStudentRepository;
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
    @Autowired
    private TestOnlineMapper testOnlineMapper;
    @Autowired
    private TestOnlineStudentRepository testOnlineStudentRepository;

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

        List<TestOnlineDTO> testOnlineDTOList = model.getTestOnlines().stream().map(testOnlineMapper::toTestOnlineDTO).collect(Collectors.toList());

        itemOnlineDTOS.sort(Comparator.comparingInt(ItemOnlineDTO::getOrderTop));

        TopicOnlineDetail topicOnlineDetail = TopicOnlineDetail.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .createdBy(model.getCreatedBy())
                .itemOnlineDTOList(itemOnlineDTOS)
                .testOnlineDTOList(testOnlineDTOList)
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return topicOnlineDetail;
    }

    /*public TopicOnlineDetail toTopicOnlineAndStudentDetail(TopicOnline model, Student student){
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
    }*/

    public TopicOnlineDetailResponse toTopicOnlineAndStudentDetailResponse(TopicOnline model, Student student){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        List<ItemOnlineDetail> itemOnlineDTOSDetailList = new ArrayList<>();
        for (ItemOnline itemOnline: model.getItemOnlines()) {
            ItemOnlineDetail itemOnlineDetail = itemOnlineMapper.toItemOnlineStudentDetail(itemOnline, student);
            itemOnlineDTOSDetailList.add(itemOnlineDetail);
        }

        itemOnlineDTOSDetailList.sort(Comparator.comparingInt(ItemOnlineDetail::getOrderTop));

        List<TestOnlineResponseDTO> testOnlineResponseDTOS = new ArrayList<>();
        for (TestOnline testOnline: model.getTestOnlines()) {
            boolean status;
            TestOnlineStudent testOnlineStudent = testOnlineStudentRepository.findByTestOnlineAndStudentAndStatus(testOnline, student, true);
            if (testOnlineStudent != null){
                status = testOnlineStudent.isStatus();
            }
            status = false;
            TestOnlineResponseDTO testOnlineResponseDTO = testOnlineMapper.toTestOnlineResponseDTO(testOnline, status);
            testOnlineResponseDTOS.add(testOnlineResponseDTO);
        }

        TopicOnlineDetailResponse topicOnlineDetail = TopicOnlineDetailResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .orderTop(model.getOrderTop())
                .createdBy(model.getCreatedBy())
                .itemOnlineDetailList(itemOnlineDTOSDetailList)
                .testOnlineResponseDTOList(testOnlineResponseDTOS)
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return topicOnlineDetail;
    }
}
