package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.item_slot.ItemSlotResponse;
import com.englishacademy.EnglishAcademy.dtos.slot.SlotResponseDetail;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDetail;
import com.englishacademy.EnglishAcademy.dtos.test_offline.TestOfflineResponse;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ItemSlotMapper;
import com.englishacademy.EnglishAcademy.mappers.SubjectMapper;
import com.englishacademy.EnglishAcademy.models.subject.CreateSubject;
import com.englishacademy.EnglishAcademy.models.subject.EditSubject;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ClassesRepository classesRepository;
    private final CourseOfflineStudentRepository courseOfflineStudentRepository;
    private final ClassesSlotRepository classesSlotRepository;
    private final ItemSlotRepository itemSlotRepository;
    private final ClassesTestOfflineRepository classesTestOfflineRepository;
    private final ItemSlotMapper itemSlotMapper;
    private final CourseOfflineRepository courseOfflineRepository;


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

    @Override
    public SubjectDetail getDetailByUser(String slug, Long userId, Long classId) {
        Subject subject = subjectRepository.findBySlug(slug);
        if (subject == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        // check
        Classes classes = classesRepository.findById(classId).orElseThrow(()-> new AppException(ErrorCode.NOTFOUND));

        // get list slotResponse
        List<SlotResponseDetail> slotResponseDetailList = new ArrayList<>();
        for (Slot slot: subject.getSlots()) {
            ClassesSlot classesSlot = classesSlotRepository.findByClassesAndSlot(classes, slot);
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
                    .findByTestOfflineAndClasses(testOffline, classes);
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

    @Override
    public SubjectDTO create(CreateSubject createSubject) {
        CourseOffline courseOffline = courseOfflineRepository.findById(createSubject.getCourseOfflineId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        Subject subjectExisting = subjectRepository.findBySlug(createSubject.getName().toLowerCase().replace(" ", "-"));
        if (subjectExisting != null) throw new AppException(ErrorCode.SUBJECT_EXISTED);
        Subject subject = Subject.builder()
                .name(createSubject.getName())
                .slug(createSubject.getName().toLowerCase().replace(" ", "-"))
                .totalSlot(createSubject.getTotalSlot())
                .orderTop(createSubject.getOrderTop())
                .createdBy("Demo")
                .modifiedBy("Demo")
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .courseOffline(courseOffline)
                .build();
        subjectRepository.save(subject);
        return subjectMapper.toSubjectDTO(subject);
    }

    @Override
    public SubjectDTO edit(EditSubject editSubject) {
        CourseOffline courseOffline = courseOfflineRepository.findById(editSubject.getCourseOfflineId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        Subject subject = subjectRepository.findById(editSubject.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOTFOUND));

        // Check if a subject with the new slug already exists
        String newSlug = editSubject.getName().toLowerCase().replace(" ", "-");
        Subject subjectExisting = subjectRepository.findBySlug(newSlug);
        if (subjectExisting != null && !subjectExisting.getId().equals(editSubject.getId())) {
            throw new AppException(ErrorCode.SUBJECT_EXISTED);
        }

        // Update the subject fields
        subject.setName(editSubject.getName());
        subject.setSlug(newSlug);
        subject.setOrderTop(editSubject.getOrderTop());
        subject.setTotalSlot(editSubject.getTotalSlot());
        subject.setCourseOffline(courseOffline);
        subject.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        // Save the updated subject
        subjectRepository.save(subject);

        // Return the updated subject as a DTO
        return subjectMapper.toSubjectDTO(subject);
    }

    @Override
    public void delete(Long[] ids) {
        subjectRepository.deleteAllById(List.of(ids));
    }

    @Override
    public SubjectDTO getBySlug(String slug) {
        Subject subject = subjectRepository.findBySlug(slug);
        if (subject == null) throw new AppException(ErrorCode.NOTFOUND);
        return subjectMapper.toSubjectDTO(subject);
    }
}
