package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.dtos.subject.SubjectDTO;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CourseOfflineMapper;
import com.englishacademy.EnglishAcademy.mappers.SubjectMapper;
import com.englishacademy.EnglishAcademy.models.course_offline.CreateCourseOffline;
import com.englishacademy.EnglishAcademy.models.course_offline.EditCourseOffline;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.CourseOfflineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseOfflineServiceImpl implements CourseOfflineService {
    private final CourseOfflineRepository courseOfflineRepository;
    private final CourseOfflineStudentRepository courseOfflineStudentRepository;
    private final StudentRepository studentRepository;
    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;
    private final CourseOfflineMapper courseOfflineMapper;
    private final SubjectMapper subjectMapper;
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

    @Override
    public CourseOfflineDTO findBySlug(String slug) {
        CourseOffline courseOffline = courseOfflineRepository.findBySlug(slug);
        if (courseOffline == null) throw new AppException(ErrorCode.COURSE_NOTFOUND);
        return courseOfflineMapper.toCourseOfflineDTO(courseOffline);
    }

    @Override
    public CourseOfflineDTO create(CreateCourseOffline model, User user) {
        CourseOffline courseOfflineExisting = courseOfflineRepository.findBySlug(model.getName().toLowerCase().replace(" ", "-"));
        if (courseOfflineExisting != null) throw new AppException(ErrorCode.COURSE_EXISTED);
        CourseOffline courseOffline = CourseOffline.builder()
                .name(model.getName())
                .slug(model.getName().toLowerCase().replace(" ", "-"))
                .price(model.getPrice())
                .status(0)
                .trailer(model.getTrailer())
                .level(model.getLevel())
                .trailer(model.getTrailer())
                .language(model.getLanguage())
                .image(model.getImage())
                .description(model.getDescription())
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        courseOfflineRepository.save(courseOffline);
        return courseOfflineMapper.toCourseOfflineDTO(courseOffline);
    }

    @Override
    public CourseOfflineDTO edit(EditCourseOffline model, User user) {
        CourseOffline courseOfflineExisting = courseOfflineRepository.findById(model.getId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        CourseOffline courseOfflineExisting1 = courseOfflineRepository.findBySlug(model.getName().toLowerCase().replace(" ", "-"));
        if (courseOfflineExisting1 != null) throw new AppException(ErrorCode.COURSE_EXISTED);
        courseOfflineExisting.setName(model.getName());
        courseOfflineExisting.setSlug(model.getName().toLowerCase().replace(" ", "-"));
        courseOfflineExisting.setPrice(model.getPrice());
        courseOfflineExisting.setImage(model.getImage());
        courseOfflineExisting.setDescription(model.getDescription());
        courseOfflineExisting.setLevel(model.getLevel());
        courseOfflineExisting.setLanguage(model.getLanguage());
        courseOfflineExisting.setTrailer(model.getTrailer());
        courseOfflineExisting.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        courseOfflineRepository.save(courseOfflineExisting);
        return courseOfflineMapper.toCourseOfflineDTO(courseOfflineExisting);
    }

    @Override
    public void delete(Long[] ids) {
        courseOfflineRepository.deleteAllById(List.of(ids));
    }

    @Override
    public CourseOfflineDetail getDetailTeacher(String slug, Long id) {
        // Tìm sinh viên theo studentId
        CourseOffline courseOffline = courseOfflineRepository.findBySlug(slug);
        if (courseOffline == null) {
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

    @Override
    public List<CourseOfflineDTO> findByUser(Long id, Long classId) {
        Classes classes = classesRepository.findById(classId).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOTFOUND));
        List<CourseOffline> courseOfflineList = new ArrayList<>();
        for (CourseOfflineStudent courseOfflineStudent: classes.getCourseOfflineStudents()) {
            courseOfflineList.add(courseOfflineStudent.getCourseOffline());
        }
        return courseOfflineList.stream().map(courseOfflineMapper::toCourseOfflineDTO).collect(Collectors.toList());
    }
}
