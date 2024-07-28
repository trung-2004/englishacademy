package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.review.ReviewDTO;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.CourseOnlineMapper;
import com.englishacademy.EnglishAcademy.mappers.ReviewMapper;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.models.course_online.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.course_online.EditCourseOnline;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.CourseOnlineService;
import com.englishacademy.EnglishAcademy.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseOnlineServiceImpl implements CourseOnlineService {
    private final CourseOnlineMapper courseOnlineMapper;
    private final CourseOnlineRepository courseOnlineRepository;
    private final StorageService storageService;
    private final StudentRepository studentRepository;
    private final ReviewRepository reviewRepository;
    private final TestOnlineStudentRepository testOnlineStudentRepository;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;
    private final CategoryRepository categoryRepository;
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
        String code = model.getName().toLowerCase().replace(" ", "-");
        CourseOnline courseOnlineExist = courseOnlineRepository.findBySlug(code);
        if (courseOnlineExist != null) throw new AppException(ErrorCode.COURSE_EXISTED);
        Category category = categoryRepository.findById(model.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        //String generatedFileName = storageService.storeFile(model.getImage());
        CourseOnline courseOnline = CourseOnline.builder()
                .name(model.getName())
                .slug(model.getName().toLowerCase().replace(" ", "-"))
                .image(model.getImage())
                .price(model.getPrice())
                .description(model.getDescription())
                .level(model.getLevel())
                .language(model.getLanguage())
                .status(0)
                .star(0.0)
                .trailer(model.getTrailer())
                .category(category)
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
        courseOnline.setImage(model.getImage());
        courseOnline.setDescription(model.getDescription());
        courseOnline.setLevel(model.getLevel());
        courseOnline.setLanguage(model.getLanguage());
        courseOnline.setTrailer(model.getTrailer());
        courseOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        courseOnlineRepository.save(courseOnline);

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

    @Override
    public List<CourseOnlineDTO> getCourseTop6() {
        List<CourseOnlineDTO> courseOnlineDTOS = courseOnlineRepository.findAllCourseLimit()
                .stream().map(courseOnlineMapper::toCourseOnlineDTO).collect(Collectors.toList());
        return courseOnlineDTOS;
    }

    @Override
    public List<CourseOnlineDTO> getCourseTopToeic(Integer score) {
        List<CourseOnline> courseOnlineDTOnlineList = new ArrayList<>();
        if (score >= 0 && score <= 300) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(3L, List.of(0,1,2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else if (score > 300 && score <= 600) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(3L, List.of(1,2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else if (score > 600 && score <= 800) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(3L, List.of(2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else if (score > 800 && score <= 990) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(3L, List.of(3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(3L, List.of(0,1,2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        }
        List<CourseOnlineDTO> courseOnlineDTOList = courseOnlineDTOnlineList.stream().map(courseOnlineMapper::toCourseOnlineDTO).collect(Collectors.toList());
        return courseOnlineDTOList;
    }

    @Override
    public List<CourseOnlineDTO> getCourseTopIelts(Integer score) {
        List<CourseOnline> courseOnlineDTOnlineList = new ArrayList<>();
        if (score >= 0 && score <= 300) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(4L, List.of(0,1,2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else if (score > 300 && score <= 600) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(4L, List.of(1,2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else if (score > 600 && score <= 800) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(4L, List.of(2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else if (score > 800 && score <= 990) {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(4L, List.of(3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        } else {
            List<CourseOnline> courseOnlineDTOS = courseOnlineRepository.findAllByCategoryIdAndLevelIn(4L, List.of(0,1,2,3));
            courseOnlineDTOnlineList.addAll(courseOnlineDTOS);
        }
        List<CourseOnlineDTO> courseOnlineDTOList = courseOnlineDTOnlineList.stream().map(courseOnlineMapper::toCourseOnlineDTO).collect(Collectors.toList());
        return courseOnlineDTOList;
    }

    @Override
    public List<CourseOnlineDTO> getCourseRelated(String slug) {
        CourseOnline courseOnline = courseOnlineRepository.findBySlug(slug);
        if (courseOnline == null) throw new AppException(ErrorCode.NOTFOUND);
        List<CourseOnline> courseOnlines = courseOnlineRepository.findRelatedCoursesByCategoryId(courseOnline.getCategory().getId(), courseOnline.getId());
        return courseOnlines.stream().map(courseOnlineMapper::toCourseOnlineDTO).collect(Collectors.toList());
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
