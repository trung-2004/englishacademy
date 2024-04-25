package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CourseOnlineMapper;
import com.englishacademy.EnglishAcademy.mappers.ReviewMapper;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.models.courseOnline.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.courseOnline.EditCourseOnline;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ICourseOnlineService;
import com.englishacademy.EnglishAcademy.services.IStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseOnlineService implements ICourseOnlineService {
    private final CourseOnlineMapper courseOnlineMapper;
    private final CourseOnlineRepository courseOnlineRepository;
    private final IStorageService storageService;
    private final StudentRepository studentRepository;
    private final ReviewRepository reviewRepository;
    private final TestOnlineStudentRepository testOnlineStudentRepository;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;
    private final ReviewMapper reviewMapper;
    private final TopicOnlineMapper topicOnlineMapper;

    @Override
    public List<CourseOnlineDTO> findAll() {
        return courseOnlineRepository.findAll().stream().map(courseOnlineMapper::toCourseOnlineDTO).toList();
    }

    @Override
    public List<CourseOnlineResponse> findAllByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        List<CourseOnline> courseOnlineStudentList = student.getCourseOnlineStudents()
                .stream().map(CourseOnlineStudent::getCourseOnline).toList();

        List<CourseOnlineResponse> courseOnlineResponseList = new ArrayList<>();
        for (CourseOnline courseOnline: courseOnlineStudentList) {
            Long Totalduration = getTotalDurationCourse(courseOnline);
            Long Realduration = getRealDurationCourse(courseOnline, student);

            double percentage = (double) Realduration / Totalduration * 100;
            // Làm tròn phần trăm thành một số nguyên
            int roundedPercentage = (int) Math.round(percentage);

            boolean check = true;
            for (TopicOnline topicOnline: courseOnline.getTopicOnlines()) {
                for (TestOnline testOnline: topicOnline.getTestOnlines()) {
                    TestOnlineStudent testOnlineStudent = testOnlineStudentRepository.findByTestOnlineAndStudentAndStatus(testOnline, student, true);
                    if (testOnlineStudent == null){
                        check = false;
                    }
                }
            }

            // create
            CourseOnlineResponse courseOnlineResponse = CourseOnlineResponse.builder()
                    .id(courseOnline.getId())
                    .name(courseOnline.getName())
                    .slug(courseOnline.getSlug())
                    .image(courseOnline.getImage())
                    .price(courseOnline.getPrice())
                    .level(courseOnline.getLevel())
                    .progress(roundedPercentage)
                    .status(check)
                    .language(courseOnline.getLanguage())
                    .createdDate(courseOnline.getCreatedDate())
                    .build();
            // add
            courseOnlineResponseList.add(courseOnlineResponse);
        }

        return courseOnlineResponseList;
    }

    @Override
    public CourseOnlineDTO findBySlug(String slug) {
        CourseOnline courseOnline = courseOnlineRepository.findBySlug(slug);
        if (courseOnline == null) {
            throw new AppException(ErrorCode.NOTFOUND);
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

        courseOnline.setCreatedBy("Demo");
        courseOnline.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        courseOnline.setModifiedBy("Demo");
        courseOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        return courseOnlineMapper.toCourseOnlineDTO(courseOnlineRepository.save(courseOnline));
    }

    @Override
    public CourseOnlineDTO edit(EditCourseOnline model) {
        CourseOnline courseOnline = courseOnlineRepository.findById(model.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

        courseOnline.setName(model.getName());
        courseOnline.setSlug(model.getName().toLowerCase().replace(" ", "-"));
        courseOnline.setPrice(model.getPrice());
        courseOnline.setDescription(model.getDescription());
        courseOnline.setLevel(model.getLevel());
        courseOnline.setLanguage(model.getLanguage());
        courseOnline.setTrailer(model.getTrailer());
        courseOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));

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
        if (model == null) throw new AppException(ErrorCode.COURSE_NOTFOUND);

        // Map từ topicOnlines sang TopicOnlineDetail và lưu vào danh sách
        List<TopicOnlineDetail> topicOnlineDetails = model.getTopicOnlines().stream()
                .map(topicOnlineMapper::toTopicOnlineDetail)
                .collect(Collectors.toList());

        // Sắp xếp danh sách theo thứ tự mong muốn (ví dụ: theo id)
        topicOnlineDetails.sort(Comparator.comparingInt(TopicOnlineDetail::getOrderTop));

        List<ReviewDTO> reviewDTOS = reviewRepository.findAllByCourseOnline(model).stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
        reviewDTOS.sort(Comparator.comparingLong(ReviewDTO::getId));

        Long duration = 0L;
        for (TopicOnline topicOnline: model.getTopicOnlines()) {
            for (ItemOnline itemOnline: topicOnline.getItemOnlines()) {
                duration += itemOnline.getDuration();
            }
        }

        CourseOnlineDetail courseOnlineDetail = CourseOnlineDetail.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .image(model.getImage())
                .price(model.getPrice())
                .description(model.getDescription())
                .level(model.getLevel())
                .language(model.getLanguage())
                .status(model.getStatus())
                .star(model.getStar())
                .duration(convertSecondtoHour(duration))
                .reviewList(reviewDTOS)
                .trailer(model.getTrailer())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .topicOnlineDetailList(topicOnlineDetails)
                .build();

        return courseOnlineDetail;
    }
    private String convertSecondtoHour(Long seconds){
        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        return hours + " hours " + minutes + " minutes";
    }
    private Long getTotalDurationCourse(CourseOnline courseOnline) {
        Long duration = 0L;
        for (TopicOnline topicOnline: courseOnline.getTopicOnlines()) {
            for (ItemOnline itemOnline: topicOnline.getItemOnlines()) {
                duration += itemOnline.getDuration();
            }
        }
        return duration;
    }
    private Long getRealDurationCourse(CourseOnline courseOnline, Student student) {
        Long duration = 0L;
        for (TopicOnline topicOnline: courseOnline.getTopicOnlines()) {
            for (ItemOnline itemOnline: topicOnline.getItemOnlines()) {
                ItemOnlineStudent itemOnlineStudent = itemOnlineStudentRepository.findByItemOnlineAndStudent(itemOnline, student);
                if (itemOnlineStudent.isStatus()){
                    duration+= itemOnline.getDuration();
                }
                else {

                }
            }
        }
        return duration;
    }
}
