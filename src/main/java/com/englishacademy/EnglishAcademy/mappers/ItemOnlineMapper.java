package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.questionItemOnline.QuestionItemOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.ItemOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemOnlineMapper {
    @Autowired
    private QuestionItemOnlineMapper questionItemOnlineMapper;
    @Autowired
    private ItemOnlineStudentRepository itemOnlineStudentRepository;
    @Autowired
    private StudentRepository studentRepository;

    public ItemOnlineDTO toItemOnlineDTO(ItemOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        ItemOnlineDTO itemOnlineDTO = ItemOnlineDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .pathUrl(model.getPathUrl())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return itemOnlineDTO;
    }

    public ItemOnlineDTO toItemOnlineStudentDTO(ItemOnline model, Student student){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        ItemOnlineStudent itemOnlineStudent = itemOnlineStudentRepository.findByItemOnlineAndStudent(model, student);

        ItemOnlineDTO itemOnlineDTO = ItemOnlineDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .pathUrl(model.getPathUrl())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();

        if (itemOnlineStudent != null){
            itemOnlineDTO.setStatus(itemOnlineStudent.isStatus());
            itemOnlineDTO.setLastAccessed(itemOnlineStudent.getLastAccessed());
        }

        return itemOnlineDTO;
    }

    public ItemOnlineDetail toItemOnlineDetail(ItemOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        List<QuestionItemOnlineDTO> questionItemOnlineDTOList = model.getQuestionItemOnlines().stream().map(questionItemOnlineMapper::toQuestionItemOnlineDTO).collect(Collectors.toList());

        questionItemOnlineDTOList.sort(Comparator.comparingInt(QuestionItemOnlineDTO::getOrderTop));

        ItemOnlineDetail itemOnlineDetail = ItemOnlineDetail.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .pathUrl(model.getPathUrl())
                .questionItemOnlineDTOList(questionItemOnlineDTOList)
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return itemOnlineDetail;
    }
}
