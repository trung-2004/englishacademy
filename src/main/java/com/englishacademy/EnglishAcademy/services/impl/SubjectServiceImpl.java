package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotResponse;
import com.englishacademy.EnglishAcademy.dtos.slot.SlotResponseDetail;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.dtos.test_offline.TestOfflineResponse;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ItemSlotMapper;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final CourseOfflineStudentRepository courseOfflineStudentRepository;
    private final ClassesSlotRepository classesSlotRepository;
    private final ItemSlotRepository itemSlotRepository;
    private final ClassesTestOfflineRepository classesTestOfflineRepository;
    private final ItemSlotMapper itemSlotMapper;


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

        // get list test offline
        List<TestOfflineResponse> testOfflineResponseList = new ArrayList<>();
        for (TestOffline testOffline: subject.getTestOfflines()) {
            ClassesTestOffline classesTestOffline = classesTestOfflineRepository
                    .findByTestOfflineAndClasses(testOffline, student.getClasses());
            if (classesTestOffline != null) {
                TestOfflineResponse testOfflineResponse = TestOfflineResponse.builder()
                        .id(testOffline.getId())
                        .title(testOffline.getName())
                        .slug(testOffline.getSlug())
                        .startDate(testOffline.getStartDate())
                        .endtDate(testOffline.getEndDate())
                        .retakeTestId(testOffline.getRetakeTestId())
                        .totalQuestion(testOffline.getTotalQuestion())
                        .pastMark(testOffline.getPastMark())
                        .totalMark(testOffline.getTotalMark())
                        .createdBy(testOffline.getCreatedBy())
                        .createdDate(testOffline.getCreatedDate())
                        .modifiedBy(testOffline.getModifiedBy())
                        .modifiedDate(testOffline.getModifiedDate())
                        .build();
                testOfflineResponseList.add(testOfflineResponse);
            }
        }

        // create SubjectDeatail
        SubjectDetail subjectDetail = SubjectDetail.builder()
                .id(subject.getId())
                .name(subject.getName())
                .slug(subject.getSlug())
                .totalSlot(subject.getTotalSlot())
                .orderTop(subject.getOrderTop())
                .slotResponseDetailList(slotResponseDetailList)
                .testOfflineResponseList(testOfflineResponseList)
                .createdBy(subject.getCreatedBy())
                .createdDate(subject.getCreatedDate())
                .modifiedBy(subject.getModifiedBy())
                .modifiedDate(subject.getModifiedDate())
                .build();

        return subjectDetail;
    }
}
