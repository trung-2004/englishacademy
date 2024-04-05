package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.courseOnlineStudent.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
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
    private ItemOnlineStudentRepository itemOnlineStudentRepository;
    @Autowired
    private CourseOnlineStudentRepository courseOnlineStudentRepository;
    @Autowired
    private CourseOnlineMapper courseOnlineMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public CourseOnlineStudentDTO buyCourse(CreateCourseOnlineStudent model) {
        Optional<Student> studentOptional  = studentRepository.findById(model.getStudentId());
        Optional<CourseOnline> courseOnlineOptional = courseOnlineRepository.findById(model.getCourseOnlineId());
        if (!studentOptional.isPresent() || !courseOnlineOptional.isPresent()) throw new AppException(ErrorCode.NOTFOUND);

        Student student = studentOptional.get();
        CourseOnline courseOnline = courseOnlineOptional.get();

        // check purchased
        CourseOnlineStudent courseOnlineStudentExsiting = courseOnlineStudentRepository.findByCourseOnlineAndStudent(courseOnline, student);
        if (courseOnlineStudentExsiting != null) throw new AppException(ErrorCode.COURSE_PURCHASED);

        CourseOnlineStudent courseOnlineStudent = new CourseOnlineStudent();
        courseOnlineStudent.setCourseOnline(courseOnline);
        courseOnlineStudent.setStudent(student);
        courseOnlineStudent.setPaymentMethod(model.getPaymentMethod());
        courseOnlineStudent.setTotalPrice(courseOnline.getPrice());
        courseOnlineStudent.setCreatedBy("Demo");
        courseOnlineStudent.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        courseOnlineStudent.setModifiedBy("Demo");
        courseOnlineStudent.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        courseOnlineStudentRepository.save(courseOnlineStudent);

        for (TopicOnline topicOnline: courseOnline.getTopicOnlines()) {
            for (ItemOnline itemOnline: topicOnline.getItemOnlines()) {
                ItemOnlineStudent itemOnlineStudent = ItemOnlineStudent.builder()
                        .itemOnline(itemOnline)
                        .student(student)
                        .status(false)
                        .build();
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

    @Override
    public boolean checkCourseOnlineRegistered(String slug, Long studentId) {
        // find courseOnline by slug
        CourseOnline courseOnline = courseOnlineRepository.findBySlug(slug);
        if (courseOnline == null) throw new AppException(ErrorCode.NOTFOUND);
        // find student by studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));
        // check registed
        CourseOnlineStudent courseOnlineStudentExsiting = courseOnlineStudentRepository.findByCourseOnlineAndStudent(courseOnline, student);
        if (courseOnlineStudentExsiting != null) return true;
        else return false;
    }
}
