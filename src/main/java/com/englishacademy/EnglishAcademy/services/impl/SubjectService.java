package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotDTO;
import com.englishacademy.EnglishAcademy.dtos.itemSlot.ItemSlotResponse;
import com.englishacademy.EnglishAcademy.dtos.slot.SlotResponseDTO;
import com.englishacademy.EnglishAcademy.dtos.slot.SlotResponseDetail;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ItemSlotMapper;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements ISubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOfflineStudentRepository courseOfflineStudentRepository;
    @Autowired
    private ClassesSlotRepository classesSlotRepository;
    @Autowired
    private ItemSlotRepository itemSlotRepository;

    @Autowired
    private ItemSlotMapper itemSlotMapper;


    @Override
    public SubjectDetail getDetail(String slug, Long studentId) {
        Subject subject = subjectRepository.findBySlug(slug);
        if (subject == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
        if (student.getClasses() == null){
            throw new AppException(ErrorCode.CLASS_NOTFOUND);
        }
        // check
        CourseOfflineStudent courseOfflineStudent = courseOfflineStudentRepository
                .findByClassesAndCourseOffline(student.getClasses(), subject.getCourseOffline());
        if (courseOfflineStudent == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        // get list slotResponse
        List<SlotResponseDetail> slotResponseDetailList = new ArrayList<>();
        for (Slot slot: subject.getSlots()) {
            ClassesSlot classesSlot = classesSlotRepository.findByClassesAndSlot(student.getClasses(), slot);
            List<ItemSlotResponse> itemSlotResponseList = itemSlotRepository.findAllByClassesSlot(classesSlot)
                    .stream().map(itemSlotMapper::toItemSlotResponse).collect(Collectors.toList());
            itemSlotResponseList.sort(Comparator.comparingInt(ItemSlotResponse::getOrderTop));

            SlotResponseDetail slotResponseDetail = SlotResponseDetail.builder()
                    .id(slot.getId())
                    .name(slot.getName())
                    .slug(slot.getSlug())
                    .orderTop(slot.getOrderTop())
                    .itemSlotResponseList(itemSlotResponseList)
                    .modifiedDate(slot.getModifiedDate())
                    .modifiedBy(slot.getModifiedBy())
                    .createdDate(slot.getCreatedDate())
                    .createdBy(slot.getCreatedBy())
                    .build();
            if (classesSlot != null){
                slotResponseDetail.setTime(classesSlot.getTime());
                slotResponseDetailList.add(slotResponseDetail);
            } else {

            }
        }
        // sort
        slotResponseDetailList.sort(Comparator.comparingInt(SlotResponseDetail::getOrderTop));

        // create SubjectDeatail
        SubjectDetail subjectDetail = SubjectDetail.builder()
                .id(subject.getId())
                .name(subject.getName())
                .slug(subject.getSlug())
                .totalSlot(subject.getTotalSlot())
                .orderTop(subject.getOrderTop())
                .slotResponseDetailList(slotResponseDetailList)
                .createdBy(subject.getCreatedBy())
                .createdDate(subject.getCreatedDate())
                .modifiedBy(subject.getModifiedBy())
                .modifiedDate(subject.getModifiedDate())
                .build();

        return subjectDetail;
    }
}
