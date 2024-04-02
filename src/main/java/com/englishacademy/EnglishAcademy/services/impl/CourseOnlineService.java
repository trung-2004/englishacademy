package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CourseOnlineMapper;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.models.courseOnline.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.courseOnline.EditCourseOnline;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineService;
import com.englishacademy.EnglishAcademy.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseOnlineService implements ICourseOnlineService {
    @Autowired
    private CourseOnlineMapper courseOnlineMapper;
    @Autowired
    private CourseOnlineRepository courseOnlineRepository;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOnlineStudentRepository courseOnlineStudentRepository;


    @Override
    public List<CourseOnlineDTO> findAll() {
        return courseOnlineRepository.findAll().stream().map(courseOnlineMapper::toCourseOnlineDTO).toList();
    }

    @Override
    public List<CourseOnlineDTO> findAllByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        List<CourseOnline> courseOnlineStudentList = student.getCourseOnlineStudents().stream().map(CourseOnlineStudent::getCourseOnline).toList();
        return courseOnlineStudentList.stream().map(courseOnlineMapper::toCourseOnlineDTO).toList();
    }

    @Override
    public CourseOnlineDTO findBySlug(String slug) {
        CourseOnline courseOnline = courseOnlineRepository.findBySlug(slug);
        if (courseOnline == null) {
            throw new RuntimeException("Not Found");
        }
        return courseOnlineMapper.toCourseOnlineDTO(courseOnline);
    }

    @Override
    public CourseOnlineDTO create(CreateCourseOnline model) {
        CourseOnline courseOnlineExist = courseOnlineRepository.findBySlug(model.getName().toLowerCase().replace(" ", "-"));
        if (courseOnlineExist != null) {
            throw new AppException(ErrorCode.COURSE_EXISTED);
        }
        String generatedFileName = storageService.storeFile(model.getImage());
        CourseOnline courseOnline = CourseOnline.builder()
                .name(model.getName())
                .slug(model.getName().toLowerCase().replace(" ", "-"))
                .image("http://localhost:8080/api/v1/FileUpload/files/"+generatedFileName)
                .price(model.getPrice())
                .description(model.getDescription())
                .level(model.getLevel())
                .language(model.getLanguage())
                .status(0)
                .star(0.0)
                .trailer(model.getTrailer())
                .build();

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // Chỉ định múi giờ của bạn (ví dụ: Asia/Ho_Chi_Minh)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        Timestamp timestamp = Timestamp.from(zonedDateTime.toInstant());

        courseOnline.setCreatedBy("Demo");
        courseOnline.setCreatedDate(timestamp);
        courseOnline.setModifiedBy("Demo");
        courseOnline.setModifiedDate(timestamp);
        return courseOnlineMapper.toCourseOnlineDTO(courseOnlineRepository.save(courseOnline));
    }

    @Override
    public CourseOnlineDTO edit(EditCourseOnline model) {
        CourseOnline courseOnline = courseOnlineRepository.findById(model.getId())
                .orElseThrow(() -> new RuntimeException("Not Found"));

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // Chỉ định múi giờ của bạn (ví dụ: Asia/Ho_Chi_Minh)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        Timestamp timestamp = Timestamp.from(zonedDateTime.toInstant());

        courseOnline.setName(model.getName());
        courseOnline.setSlug(model.getName().toLowerCase().replace(" ", "-"));
        courseOnline.setPrice(model.getPrice());
        courseOnline.setDescription(model.getDescription());
        courseOnline.setLevel(model.getLevel());
        courseOnline.setLanguage(model.getLanguage());
        courseOnline.setTrailer(model.getTrailer());
        courseOnline.setModifiedDate(timestamp);

        if (model.getImage().isEmpty()){

        } else {
            String generatedFileName = storageService.storeFile(model.getImage());
            courseOnline.setImage("http://localhost:8080/api/v1/FileUpload/files/"+generatedFileName);
        }

        return courseOnlineMapper.toCourseOnlineDTO(courseOnlineRepository.save(courseOnline));
    }

    @Override
    public void delete(Long[] ids) {
        courseOnlineRepository.deleteAllById(List.of(ids));
    }

    @Override
    public CourseOnlineDetail getDetail(String slug) {
        CourseOnline model = courseOnlineRepository.findBySlug(slug);
        if (model == null) {
            throw new RuntimeException("Not Found");
        }
        return courseOnlineMapper.toCourseOnlineDetail(model);
    }
}
