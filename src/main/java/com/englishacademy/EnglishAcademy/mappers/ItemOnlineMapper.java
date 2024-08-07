package com.englishacademy.EnglishAcademy.mappers;

import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.question_item_online.QuestionItemOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.ItemOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemOnlineMapper {
    private final QuestionItemOnlineMapper questionItemOnlineMapper;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;

    public ItemOnlineDTO toItemOnlineDTO(ItemOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }

        ItemOnlineDTO itemOnlineDTO = ItemOnlineDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .pathUrl(model.getPathUrl())
                .topicId(model.getTopicOnline().getId())
                .courseId(model.getTopicOnline().getCourseOnline().getId())
                .courseSlug(model.getTopicOnline().getCourseOnline().getSlug())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return itemOnlineDTO;
    }

    public ItemOnlineDTOResponse toItemOnlineDTOResponse(ItemOnline model){
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        List<QuestionItemOnlineDTO> questionItemOnlineDTOList = model.getQuestionItemOnlines().stream().map(questionItemOnlineMapper::toQuestionItemOnlineDTO).collect(Collectors.toList());
        questionItemOnlineDTOList.sort(Comparator.comparingInt(QuestionItemOnlineDTO::getOrderTop));

        ItemOnlineDTOResponse itemOnlineDTO = ItemOnlineDTOResponse.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .pathUrl(model.getPathUrl())
                .topicId(model.getTopicOnline().getId())
                .courseId(model.getTopicOnline().getCourseOnline().getId())
                .courseSlug(model.getTopicOnline().getCourseOnline().getSlug())
                .questionItemOnlineDTOList(questionItemOnlineDTOList)
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return itemOnlineDTO;
    }

    public ItemOnlineDetail toItemOnlineStudentDetail(ItemOnline model, Student student){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        List<QuestionItemOnlineDTO> questionItemOnlineDTOList = model.getQuestionItemOnlines().stream().map(questionItemOnlineMapper::toQuestionItemOnlineDTO).collect(Collectors.toList());

        questionItemOnlineDTOList.sort(Comparator.comparingInt(QuestionItemOnlineDTO::getOrderTop));

        ItemOnlineStudent itemOnlineStudent = itemOnlineStudentRepository.findByItemOnlineAndStudent(model, student);

        ItemOnlineDetail itemOnlineDetail = ItemOnlineDetail.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .content(model.getContent())
                .itemType(model.getItemType())
                .orderTop(model.getOrderTop())
                .duration(model.getDuration())
                .pathUrl(model.getPathUrl())
                .questionItemOnlineDTOList(questionItemOnlineDTOList)
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();

        if (itemOnlineStudent != null){
            itemOnlineDetail.setStatus(itemOnlineStudent.isStatus());
        }

        return itemOnlineDetail;
    }

    public ItemOnlineDetail toItemOnlineDetail(ItemOnline model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        List<QuestionItemOnlineDTO> questionItemOnlineDTOList = model.getQuestionItemOnlines().stream().map(questionItemOnlineMapper::toQuestionItemOnlineDTO).collect(Collectors.toList());

        questionItemOnlineDTOList.sort(Comparator.comparingInt(QuestionItemOnlineDTO::getOrderTop));

        ItemOnlineDetail itemOnlineDetail = ItemOnlineDetail.builder()
                .id(model.getId())
                .title(model.getTitle())
                .slug(model.getSlug())
                .courseSlug(model.getTopicOnline().getCourseOnline().getSlug())
                .content(model.getContent())
                .itemType(model.getItemType())
                .duration(model.getDuration())
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
