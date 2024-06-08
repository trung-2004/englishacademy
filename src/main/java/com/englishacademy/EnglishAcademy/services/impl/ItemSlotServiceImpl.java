package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.answer_student_item_slot.AnswerStudentItemSlotResponse;
import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotDetail;
import com.englishacademy.EnglishAcademy.entities.ItemSlot;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.AnswerStudentItemSlotMapper;
import com.englishacademy.EnglishAcademy.repositories.AnswerStudentItemSlotRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemSlotRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.ItemSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemSlotServiceImpl implements ItemSlotService {
    private final ItemSlotRepository itemSlotRepository;
    private final StudentRepository studentRepository;
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
}
