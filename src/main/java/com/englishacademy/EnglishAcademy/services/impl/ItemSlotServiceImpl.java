package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot.AnswerStudentItemSlotResponse;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDTO;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.AnswerStudentItemSlotMapper;
import com.englishacademy.EnglishAcademy.mappers.ItemSlotMapper;
import com.englishacademy.EnglishAcademy.models.item_slot.CreateItemSlot;
import com.englishacademy.EnglishAcademy.models.item_slot.EditItemSlot;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ItemSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemSlotServiceImpl implements ItemSlotService {
    private final ItemSlotRepository itemSlotRepository;
    private final ItemSlotMapper itemSlotMapper;
    private final StudentRepository studentRepository;
    private final ClassesRepository classesRepository;
    private final SlotRepository slotRepository;
    private final ClassesSlotRepository classesSlotRepository;
    private final AnswerStudentItemSlotMapper answerStudentItemSlotMapper;
    private final AnswerStudentItemSlotRepository answerStudentItemSlotRepository;

    @Override
    public ItemSlotDetail getDetail(String slug, Long studentId) {
        // check
        ItemSlot itemSlot = itemSlotRepository.findBySlug(slug);
        if (itemSlot == null) throw new AppException(ErrorCode.ITEMSLOT_NOTFOUND);
        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        if (!student.getClasses().equals(itemSlot.getClassesSlot().getClasses())) throw new AppException(ErrorCode.NOTFOUND);

        ItemSlotDetail itemSlotDetail = ItemSlotDetail.builder()
                .id(itemSlot.getId())
                .itemType(itemSlot.getItemType())
                .content(itemSlot.getContent())
                .title(itemSlot.getTitle())
                .slug(itemSlot.getSlug())
                .orderTop(itemSlot.getOrderTop())
                .pathUrl(itemSlot.getPathUrl())
                .startDate(itemSlot.getStartDate())
                .endDate(itemSlot.getEndDate())
                //.answerStudentItemSlotResponseListList(answerStudentItemSlotResponseList)
                .createdDate(itemSlot.getCreatedDate())
                .createdBy(itemSlot.getCreatedBy())
                .modifiedDate(itemSlot.getModifiedDate())
                .modifiedBy(itemSlot.getModifiedBy())
                .build();

        if (itemSlot.getItemType() == 0) {
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(null);
        } else if (itemSlot.getItemType() == 1) {
            List<AnswerStudentItemSlotResponse> answerStudentItemSlotResponseList = itemSlot.getAnswerStudentItemSlots()
                    .stream().map(answerStudentItemSlotMapper::toAnswerStudentItemSlotResponse).collect(Collectors.toList());
            answerStudentItemSlotResponseList.sort(Comparator.comparingLong(AnswerStudentItemSlotResponse::getId));
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(answerStudentItemSlotResponseList);
        } else if (itemSlot.getItemType() == 2) {
            List<AnswerStudentItemSlotResponse> answerStudentItemSlotResponseList = answerStudentItemSlotRepository.findAllByStudentAndItemSlot(student, itemSlot)
                    .stream().map(answerStudentItemSlotMapper::toAnswerStudentItemSlotResponse).collect(Collectors.toList());
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(answerStudentItemSlotResponseList);
        } else {
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(null);
        }

        return itemSlotDetail;
    }

    @Override
    public ItemSlotDetail getDetailByUser(String slug, Long id, Long classId) {
        ItemSlot itemSlot = itemSlotRepository.findBySlug(slug);
        if (itemSlot == null) throw new AppException(ErrorCode.ITEMSLOT_NOTFOUND);
        Classes classes = classesRepository.findById(classId).orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        // Tìm sinh viên theo studentId
        if (!classes.getId().equals(itemSlot.getClassesSlot().getClasses().getId())) throw new AppException(ErrorCode.NOTFOUND);

        ItemSlotDetail itemSlotDetail = ItemSlotDetail.builder()
                .id(itemSlot.getId())
                .itemType(itemSlot.getItemType())
                .content(itemSlot.getContent())
                .title(itemSlot.getTitle())
                .slug(itemSlot.getSlug())
                .orderTop(itemSlot.getOrderTop())
                .pathUrl(itemSlot.getPathUrl())
                .startDate(itemSlot.getStartDate())
                .endDate(itemSlot.getEndDate())
                //.answerStudentItemSlotResponseListList(answerStudentItemSlotResponseList)
                .createdDate(itemSlot.getCreatedDate())
                .createdBy(itemSlot.getCreatedBy())
                .modifiedDate(itemSlot.getModifiedDate())
                .modifiedBy(itemSlot.getModifiedBy())
                .build();

        if (itemSlot.getItemType() == 0) {
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(null);
        } else if (itemSlot.getItemType() == 1) {
            List<AnswerStudentItemSlotResponse> answerStudentItemSlotResponseList = itemSlot.getAnswerStudentItemSlots()
                    .stream().map(answerStudentItemSlotMapper::toAnswerStudentItemSlotResponse).collect(Collectors.toList());
            answerStudentItemSlotResponseList.sort(Comparator.comparingLong(AnswerStudentItemSlotResponse::getId));
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(answerStudentItemSlotResponseList);
        } else if (itemSlot.getItemType() == 2) {
                List<AnswerStudentItemSlotResponse> answerStudentItemSlotResponseList = answerStudentItemSlotRepository.findAllByItemSlot( itemSlot)
                        .stream().map(answerStudentItemSlotMapper::toAnswerStudentItemSlotResponse).collect(Collectors.toList());
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(answerStudentItemSlotResponseList);
        } else {
            itemSlotDetail.setAnswerStudentItemSlotResponseListList(null);
        }

        return itemSlotDetail;
    }

    @Override
    public ItemSlotDTO getBySlug(String slug) {
        ItemSlot itemSlot = itemSlotRepository.findBySlug(slug);
        if (itemSlot == null) throw new AppException(ErrorCode.ITEMSLOT_NOTFOUND);
        return itemSlotMapper.toItemSlotDTO(itemSlot);
    }

    @Override
    public ItemSlotDTO create(CreateItemSlot createItemSlot, User currentUser) {
        // Check if an item slot with the same slug already exists
        String slug = createItemSlot.getTitle().toLowerCase().replace(" ", "-");
        ItemSlot existingItemSlot = itemSlotRepository.findBySlug(slug);
        if (existingItemSlot != null) {
            throw new AppException(ErrorCode.ITEMSLOT_EXISTED);
        }
        Classes classes = classesRepository.findById(createItemSlot.getClassId()).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOTFOUND));
        Slot slot = slotRepository.findById(createItemSlot.getClassId()).orElseThrow(() -> new AppException(ErrorCode.SLOT_NOTFOUND));

        ClassesSlot classesSlot = classesSlotRepository.findByClassesAndSlot(classes, slot);
        if (classesSlot == null) throw new AppException(ErrorCode.NOTFOUND);

        // Create new ItemSlot entity
        ItemSlot itemSlot = ItemSlot.builder()
                .title(createItemSlot.getTitle())
                .slug(slug)
                .content(createItemSlot.getContent())
                .itemType(createItemSlot.getItemType())
                .startDate(createItemSlot.getStartDate())
                .endDate(createItemSlot.getEndDate())
                .orderTop(createItemSlot.getOrderTop())
                .pathUrl(createItemSlot.getPathUrl())
                .createdBy(currentUser.getFullName())
                .modifiedBy(currentUser.getFullName())
                .classesSlot(classesSlot)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();

        // Save the new ItemSlot entity
        itemSlotRepository.save(itemSlot);

        // Convert the saved entity to a DTO and return it
        return itemSlotMapper.toItemSlotDTO(itemSlot);
    }

    @Override
    public ItemSlotDTO edit(EditItemSlot editItemSlot, User currentUser) {
        ItemSlot itemSlot = itemSlotRepository.findById(editItemSlot.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ITEMSLOT_NOTFOUND));

        // Check if an item slot with the new slug already exists and is not the same as the current item slot
        String newSlug = editItemSlot.getTitle().toLowerCase().replace(" ", "-");
        ItemSlot existingItemSlot = itemSlotRepository.findBySlug(newSlug);
        if (existingItemSlot != null && !existingItemSlot.getId().equals(editItemSlot.getId())) {
            throw new AppException(ErrorCode.ITEMSLOT_EXISTED);
        }
        Classes classes = classesRepository.findById(editItemSlot.getClassId()).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOTFOUND));
        Slot slot = slotRepository.findById(editItemSlot.getClassId()).orElseThrow(() -> new AppException(ErrorCode.SLOT_NOTFOUND));

        ClassesSlot classesSlot = classesSlotRepository.findByClassesAndSlot(classes, slot);
        if (classesSlot == null) throw new AppException(ErrorCode.NOTFOUND);

        // Update the item slot fields
        itemSlot.setTitle(editItemSlot.getTitle());
        itemSlot.setSlug(newSlug);
        itemSlot.setContent(editItemSlot.getContent());
        itemSlot.setItemType(editItemSlot.getItemType());
        itemSlot.setStartDate(editItemSlot.getStartDate());
        itemSlot.setEndDate(editItemSlot.getEndDate());
        itemSlot.setOrderTop(editItemSlot.getOrderTop());
        itemSlot.setPathUrl(editItemSlot.getPathUrl());
        itemSlot.setClassesSlot(classesSlot);
        itemSlot.setModifiedBy(currentUser.getFullName());
        itemSlot.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        // Save the updated item slot entity
        itemSlotRepository.save(itemSlot);

        // Convert the updated entity to a DTO and return it
        return itemSlotMapper.toItemSlotDTO(itemSlot);
    }

    @Override
    public void delete(Long[] ids) {
        itemSlotRepository.deleteAllById(List.of(ids));
    }
}
