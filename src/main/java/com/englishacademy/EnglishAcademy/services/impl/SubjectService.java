package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.slot.SlotResponseDTO;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.repositories.ClassesSlotRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOfflineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.repositories.SubjectRepository;
import com.englishacademy.EnglishAcademy.services.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<SlotResponseDTO> slotResponseDTOList = new ArrayList<>();
        for (Slot slot: subject.getSlots()) {
            ClassesSlot classesSlot = classesSlotRepository.findByClassesAndSlot(student.getClasses(), slot);
            SlotResponseDTO slotResponseDTO = SlotResponseDTO.builder()
                    .id(slot.getId())
                    .name(slot.getName())
                    .slug(slot.getSlug())
                    .orderTop(slot.getOrderTop())
                    .modifiedDate(slot.getModifiedDate())
                    .modifiedBy(slot.getModifiedBy())
                    .createdDate(slot.getCreatedDate())
                    .createdBy(slot.getCreatedBy())
                    .build();
            if (classesSlot != null){
                slotResponseDTO.setTime(classesSlot.getTime());
                slotResponseDTOList.add(slotResponseDTO);
            } else {

            }
        }
        // sort
        slotResponseDTOList.sort(Comparator.comparingInt(SlotResponseDTO::getOrderTop));

        // create SubjectDeatail
        SubjectDetail subjectDetail = SubjectDetail.builder()
                .id(subject.getId())
                .name(subject.getName())
                .slug(subject.getSlug())
                .totalSlot(subject.getTotalSlot())
                .orderTop(subject.getOrderTop())
                .slotResponseList(slotResponseDTOList)
                .createdBy(subject.getCreatedBy())
                .createdDate(subject.getCreatedDate())
                .modifiedBy(subject.getModifiedBy())
                .modifiedDate(subject.getModifiedDate())
                .build();

        return subjectDetail;
    }
}
