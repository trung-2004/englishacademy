package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.itemOnline.ItemOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.mappers.ItemOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineRepository;
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

    @Override
    public ItemOnlineDetail getItemOnlineDetail(String slug) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        return itemOnlineMapper.toItemOnlineDetail(model);
    }

    /*@Override
    public ItemOnlineDTO completeItem(String slug, Long studentId) {
        ItemOnline model = itemOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()){
            throw new RuntimeException("User Not Found");
        }
        CourseOnlineStudent courseOnlineStudent = courseOnlineStudentRepository.findByCourseOnlineAndStudent(model.getTopicOnline().getCourseOnline(), student.get());
        if (courseOnlineStudent == null){
            throw new RuntimeException("Not Found");
        }

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // Chỉ định múi giờ của bạn (ví dụ: Asia/Ho_Chi_Minh)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        Timestamp timestamp = Timestamp.from(zonedDateTime.toInstant());

        itemOnlineStudent.setStatus(true);
        itemOnlineStudent.setLastAccessed(timestamp);

        itemOnlineStudentRepository.save(itemOnlineStudent);

        return itemOnlineMapper.toItemOnlineStudentDTO(model, student.get());
    }*/
}
