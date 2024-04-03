package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import com.englishacademy.EnglishAcademy.entities.CourseOffline;
import com.englishacademy.EnglishAcademy.entities.CourseOfflineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CourseOfflineMapper;
import com.englishacademy.EnglishAcademy.mappers.SubjectMapper;
import com.englishacademy.EnglishAcademy.repositories.CourseOfflineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOfflineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.ICourseOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseOfflineService implements ICourseOfflineService {
    @Autowired
    private CourseOfflineRepository courseOfflineRepository;
    @Autowired
    private CourseOfflineStudentRepository courseOfflineStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOfflineMapper courseOfflineMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Override
    public List<CourseOfflineDTO> findAll() {
        return courseOfflineRepository.findAll().stream()
                .map(courseOfflineMapper::toCourseOfflineDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseOfflineDTO> findByStudent(Long studentId) {
        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
        if (student.getClasses() == null){
            throw new AppException(ErrorCode.CLASS_NOTFOUND);
        }
        List<CourseOffline> courseOfflineList = new ArrayList<>();
        for (CourseOfflineStudent courseOfflineStudent: student.getClasses().getCourseOfflineStudents()) {
            courseOfflineList.add(courseOfflineStudent.getCourseOffline());
        }
        return courseOfflineList.stream().map(courseOfflineMapper::toCourseOfflineDTO).collect(Collectors.toList());
    }

    @Override
    public CourseOfflineDetail getDetail(String slug, Long studentId) {
        // Tìm sinh viên theo studentId
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
        if (student.getClasses() == null){
            throw new AppException(ErrorCode.CLASS_NOTFOUND);
        }
        CourseOffline courseOffline = courseOfflineRepository.findBySlug(slug);
        if (courseOffline == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        CourseOfflineStudent courseOfflineStudent = courseOfflineStudentRepository
                .findByClassesAndCourseOffline(student.getClasses(), courseOffline);
        if (courseOfflineStudent == null) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        List<SubjectDTO> subjectDTOList = courseOffline.getSubjects().stream().map(subjectMapper::toSubjectDTO).collect(Collectors.toList());
        subjectDTOList.sort(Comparator.comparingInt(SubjectDTO::getOrderTop));

        CourseOfflineDetail courseOfflineDetail = CourseOfflineDetail.builder()
                .id(courseOffline.getId())
                .name(courseOffline.getName())
                .slug(courseOffline.getSlug())
                .level(courseOffline.getLevel())
                .description(courseOffline.getDescription())
                .image(courseOffline.getImage())
                .price(courseOffline.getPrice())
                .status(courseOffline.getStatus())
                .language(courseOffline.getLanguage())
                .trailer(courseOffline.getTrailer())
                .subjectList(subjectDTOList)
                .modifiedBy(courseOffline.getModifiedBy())
                .modifiedDate(courseOffline.getModifiedDate())
                .createdDate(courseOffline.getCreatedDate())
                .createdBy(courseOffline.getCreatedBy())
                .build();
        return courseOfflineDetail;
    }
}
