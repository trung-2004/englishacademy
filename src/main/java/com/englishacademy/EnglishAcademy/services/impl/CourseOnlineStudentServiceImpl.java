package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineStudentDTOResponse;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CourseOnlineMapper;
import com.englishacademy.EnglishAcademy.mappers.StudentMapper;
import com.englishacademy.EnglishAcademy.models.course_online_student.CreateCourseOnlineStudent;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.CourseOnlineStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseOnlineStudentServiceImpl implements CourseOnlineStudentService {
    private final CourseOnlineRepository courseOnlineRepository;
    private final StudentRepository studentRepository;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;
    private final CourseOnlineStudentRepository courseOnlineStudentRepository;
    private final CourseOnlineMapper courseOnlineMapper;
    private final StudentMapper studentMapper;

    @Override
    public CourseOnlineStudentDTO buyCourse(CreateCourseOnlineStudent model, Long studentId) {
        Optional<Student> studentOptional  = studentRepository.findById(studentId);
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
        courseOnlineStudent.setCreatedBy(student.getFullName());
        courseOnlineStudent.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        courseOnlineStudent.setModifiedBy(student.getFullName());
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

    @Override
    public List<CourseOnlineStudentDTOResponse> getHistory() {
        List<CourseOnlineStudent> courseOnlineStudents = courseOnlineStudentRepository.findAll();
        List<CourseOnlineStudentDTOResponse> courseOnlineStudentDTOResponses = new ArrayList<>();
        for (CourseOnlineStudent courseOnlineStudent: courseOnlineStudents) {
            CourseOnlineStudentDTOResponse courseOnlineStudentDTOResponse = CourseOnlineStudentDTOResponse.builder()
                    .id(courseOnlineStudent.getId())
                    .courseOnlineId(courseOnlineStudent.getCourseOnline().getId())
                    .courseOnlineName(courseOnlineStudent.getCourseOnline().getName())
                    .studentName(courseOnlineStudent.getStudent().getFullName())
                    .studentId(courseOnlineStudent.getStudent().getId())
                    .totalPrice(courseOnlineStudent.getTotalPrice())
                    .paymentMethod(courseOnlineStudent.getPaymentMethod())
                    .createddate(courseOnlineStudent.getCreatedDate())
                    .build();
            courseOnlineStudentDTOResponses.add(courseOnlineStudentDTOResponse);
        }
        courseOnlineStudentDTOResponses.sort(Comparator.comparing(CourseOnlineStudentDTOResponse::getCreateddate));
        return courseOnlineStudentDTOResponses;
    }
}
