package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.item_online.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ItemOnlineMapper;
import com.englishacademy.EnglishAcademy.models.item_online.CreateItemOnline;
import com.englishacademy.EnglishAcademy.models.item_online.EditItemOnline;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ItemOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemOnlineServiceImpl implements ItemOnlineService {
    private final ItemOnlineRepository itemOnlineRepository;
    private final ItemOnlineMapper itemOnlineMapper;
    private final StudentRepository studentRepository;
    private final CourseOnlineStudentRepository courseOnlineStudentRepository;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;
    private final TopicOnlineRepository topicOnlineRepository;

    @Override
    public ItemOnlineDetail getItemOnlineDetail(String slug) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return itemOnlineMapper.toItemOnlineDetail(model);
    }

    @Override
    public void completeItem(String slug, Long studentId) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) throw new AppException(ErrorCode.ITEMONLINE_NOTFOUND);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        CourseOnlineStudent courseOnlineStudent = courseOnlineStudentRepository.findByCourseOnlineAndStudent(model.getTopicOnline().getCourseOnline(), student);
        if (courseOnlineStudent == null) throw new AppException(ErrorCode.COURSE_NOTPURCHASED);

        ItemOnlineStudent itemOnlineStudentExsiting = itemOnlineStudentRepository.findByItemOnlineAndStudent(model, student);
        if (itemOnlineStudentExsiting == null) throw new AppException(ErrorCode.NOTFOUND);

        if (itemOnlineStudentExsiting.isStatus()) {
            return;
        }

        itemOnlineStudentExsiting.setStatus(true);
        itemOnlineStudentRepository.save(itemOnlineStudentExsiting);
    }

    @Override
    public ItemOnlineDTO findBySlug(String slug) {
        ItemOnline itemOnline = itemOnlineRepository.findBySlug(slug);
        if (itemOnline == null) throw new AppException(ErrorCode.ITEMONLINE_NOTFOUND);
        return itemOnlineMapper.toItemOnlineDTO(itemOnline);
    }

    @Override
    public ItemOnlineDTO create(CreateItemOnline createItemOnline) {
        ItemOnline itemOnlineExisting = itemOnlineRepository.findBySlug(createItemOnline.getTitle().toLowerCase().replace(" ", "-"));
        if (itemOnlineExisting != null) throw new AppException(ErrorCode.ITEMONLINE_EXISTED);
        TopicOnline topicOnline = topicOnlineRepository.findById(createItemOnline.getTopicOnlineId())
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOTFOUND));

        ItemOnline itemOnline = ItemOnline.builder()
                .title(createItemOnline.getTitle())
                .slug(createItemOnline.getTitle().toLowerCase().replace(" ", "-"))
                .duration(createItemOnline.getDuration())
                .pathUrl(createItemOnline.getPathUrl())
                .orderTop(createItemOnline.getOrderTop())
                .itemType(createItemOnline.getItemType())
                .content(createItemOnline.getContent())
                .createdBy("Demo")
                .modifiedBy("Demo")
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        return itemOnlineMapper.toItemOnlineDTO(itemOnlineRepository.save(itemOnline));
    }

    @Override
    public ItemOnlineDTO edit(EditItemOnline editItemOnline) {
        ItemOnline itemOnline = itemOnlineRepository.findById(editItemOnline.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOTFOUND));

        TopicOnline topicOnline = topicOnlineRepository.findById(editItemOnline.getTopicOnlineId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));

        if (!itemOnline.getSlug().equals(editItemOnline.getTitle().toLowerCase().replace(" ", "-"))) {
            ItemOnline itemOnlineExisting = itemOnlineRepository.findBySlug(editItemOnline.getTitle().toLowerCase().replace(" ", "-"));
            if (itemOnlineExisting != null) throw new AppException(ErrorCode.TOPIC_EXISTED);
        }
        itemOnline.setTitle(editItemOnline.getTitle());
        itemOnline.setSlug(editItemOnline.getTitle().toLowerCase().replace(" ", "-"));
        itemOnline.setOrderTop(editItemOnline.getOrderTop());
        itemOnline.setDuration(editItemOnline.getDuration());
        itemOnline.setPathUrl(editItemOnline.getPathUrl());
        itemOnline.setItemType(editItemOnline.getItemType());
        itemOnline.setContent(editItemOnline.getContent());
        itemOnline.setTopicOnline(topicOnline);
        itemOnline.setModifiedBy("Demo");
        itemOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        return itemOnlineMapper.toItemOnlineDTO(itemOnlineRepository.save(itemOnline));
    }

    @Override
    public void delete(Long[] ids) {
        itemOnlineRepository.deleteAllById(List.of(ids));
    }
}
