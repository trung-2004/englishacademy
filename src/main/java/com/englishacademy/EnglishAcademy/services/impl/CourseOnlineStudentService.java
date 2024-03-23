package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.courseOnlineStudent.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.mappers.CourseOnlineMapper;
import com.englishacademy.EnglishAcademy.mappers.StudentMapper;
import com.englishacademy.EnglishAcademy.models.courseOnlineStudent.CreateCourseOnlineStudent;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CourseOnlineStudentService implements ICourseOnlineStudentService {
    @Autowired
    private CourseOnlineRepository courseOnlineRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOnlineStudentRepository courseOnlineStudentRepository;
    @Autowired
    private ItemOnlineStudentRepository itemOnlineStudentRepository;
    @Autowired
    private CourseOnlineMapper courseOnlineMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public CourseOnlineStudentDTO buyCourse(CreateCourseOnlineStudent model) {
        Optional<Student> studentOptional  = studentRepository.findById(model.getStudentId());
        Optional<CourseOnline> courseOnlineOptional = courseOnlineRepository.findById(model.getCourseOnlineId());
        if (!studentOptional.isPresent() || !courseOnlineOptional.isPresent()){
            throw new  RuntimeException("Not found");
        }

        Student student = studentOptional.get();
        CourseOnline courseOnline = courseOnlineOptional.get();

        CourseOnlineStudent courseOnlineStudentExsiting = courseOnlineStudentRepository.findByCourseOnlineAndStudent(courseOnline, student);

        if (courseOnlineStudentExsiting != null){
            throw new  RuntimeException("This course has been purchased");
        }

        CourseOnlineStudent courseOnlineStudent = new CourseOnlineStudent();
        courseOnlineStudent.setCourseOnline(courseOnline);
        courseOnlineStudent.setStudent(student);
        courseOnlineStudent.setPaymentMethod(model.getPaymentMethod());
        courseOnlineStudent.setTotalPrice(courseOnline.getPrice());

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // Chỉ định múi giờ của bạn (ví dụ: Asia/Ho_Chi_Minh)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        Timestamp timestamp = Timestamp.from(zonedDateTime.toInstant());

        courseOnlineStudent.setCreatedBy("Demo");
        courseOnlineStudent.setCreatedDate(timestamp);
        courseOnlineStudent.setModifiedBy("Demo");
        courseOnlineStudent.setModifiedDate(timestamp);

        courseOnlineStudentRepository.save(courseOnlineStudent);

        for (TopicOnline topicOnline: courseOnline.getTopicOnlines()) {
            for (ItemOnline itemOnline: topicOnline.getItemOnlines()) {
                ItemOnlineStudent itemOnlineStudent = new ItemOnlineStudent();
                    itemOnlineStudent.setStudent(student);
                    itemOnlineStudent.setItemOnline(itemOnline);
                    if (itemOnline.getOrderTop() == 1){
                        itemOnlineStudent.setStatus(true);
                        itemOnlineStudent.setLastAccessed(timestamp);
                    } else {
                        itemOnlineStudent.setStatus(false);
                        itemOnlineStudent.setLastAccessed(null);
                    }
                    itemOnlineStudent.setCreatedBy("Demo");
                    itemOnlineStudent.setCreatedDate(timestamp);
                    itemOnlineStudent.setModifiedBy("Demo");
                    itemOnlineStudent.setModifiedDate(timestamp);

                    itemOnlineStudentRepository.save(itemOnlineStudent);
            }
        }


        CourseOnlineStudentDTO courseOnlineStudentDTO = CourseOnlineStudentDTO.builder()
                .courseOnline(courseOnlineMapper.toCourseOnlineDTO(courseOnlineStudent.getCourseOnline()))
                .student(studentMapper.toStudentDTO(courseOnlineStudent.getStudent()))
                .paymentMethod(courseOnlineStudent.getPaymentMethod())
                .totalPrice(courseOnlineStudent.getTotalPrice())
                .build();
        return courseOnlineStudentDTO;
    }
}
