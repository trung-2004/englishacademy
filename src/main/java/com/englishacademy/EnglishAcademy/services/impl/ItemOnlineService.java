package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.ItemOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.IItemOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ItemOnlineService implements IItemOnlineService {
    @Autowired
    private ItemOnlineRepository itemOnlineRepository;
    @Autowired
    private ItemOnlineMapper itemOnlineMapper;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOnlineStudentRepository courseOnlineStudentRepository;
    @Autowired
    private ItemOnlineStudentRepository itemOnlineStudentRepository;

    @Override
    public ItemOnlineDetail getItemOnlineDetail(String slug) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new RuntimeException("Not Found");
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
}
