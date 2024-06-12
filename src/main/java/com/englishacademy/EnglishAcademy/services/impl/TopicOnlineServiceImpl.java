package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.topic_online.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.topic_online.TopicOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.models.topic_online.CreateTopicOnline;
import com.englishacademy.EnglishAcademy.models.topic_online.EditTopicOnline;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.TopicOnlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicOnlineServiceImpl implements TopicOnlineService {
    private final TopicOnlineRepository topicOnlineRepository;
    private final StudentRepository studentRepository;
    private final CourseOnlineRepository courseOnlineRepository;
    private final CourseOnlineStudentRepository courseOnlineStudentRepository;
    private final ItemOnlineStudentRepository itemOnlineStudentRepository;
    private final TestOnlineStudentRepository testOnlineStudentRepository;
    private final TopicOnlineMapper topicOnlineMapper;

    @Override
    public CourseOnlineTopicDetailResponse findAllByCourseSlug(String slug, Long userId) {
        CourseOnline courseOnline = courseOnlineRepository.findBySlug(slug);
        if (courseOnline == null) throw new AppException(ErrorCode.COURSE_NOTFOUND);
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOTFOUND));

        CourseOnlineStudent courseOnlineStudent = courseOnlineStudentRepository.findByCourseOnlineAndStudent(courseOnline, student);
        if (courseOnlineStudent == null) throw new AppException(ErrorCode.COURSE_NOTPURCHASED);

        List<TopicOnline> topicOnlineList = topicOnlineRepository.findAllByCourseOnline(courseOnline);

        List<TopicOnlineDetailResponse> topicOnlineDetailList = new ArrayList<>();

        for (TopicOnline topicOnline: topicOnlineList) {
            TopicOnlineDetailResponse TopicOnlineDetailResponse = topicOnlineMapper.toTopicOnlineAndStudentDetailResponse(topicOnline, student);
            topicOnlineDetailList.add(TopicOnlineDetailResponse);
        }
        topicOnlineDetailList.sort(Comparator.comparingInt(TopicOnlineDetailResponse::getOrderTop));

        // process and status
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

        CourseOnlineTopicDetailResponse courseOnlineTopicDetailResponse = CourseOnlineTopicDetailResponse.builder()
                .name(courseOnline.getName())
                .slug(courseOnline.getSlug())
                .topicOnlineDetailResponseList(topicOnlineDetailList)
                .status(check)
                .progress(roundedPercentage)
                .build();

        return courseOnlineTopicDetailResponse;
    }

    @Override
    public TopicOnlineDTO findBySlug(String slug) {
        TopicOnline topicOnline = topicOnlineRepository.findBySlug(slug);
        if (topicOnline == null) throw new AppException(ErrorCode.NOTFOUND);
        return topicOnlineMapper.toTopicOnlineDTO(topicOnline);
    }

    @Override
    public TopicOnlineDTO create(CreateTopicOnline createTopicOnline) {
        TopicOnline topicOnlineExisting = topicOnlineRepository.findBySlug(createTopicOnline.getName().toLowerCase().replace(" ", "-"));
        if (topicOnlineExisting != null) throw new AppException(ErrorCode.TOPIC_EXISTED);
        CourseOnline courseOnline = courseOnlineRepository.findById(createTopicOnline.getCourseOnlineId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));
        TopicOnline topicOnline = TopicOnline.builder()
                .name(createTopicOnline.getName())
                .slug(createTopicOnline.getName().toLowerCase().replace(" ", "-"))
                .orderTop(createTopicOnline.getOrderTop())
                .courseOnline(courseOnline)
                .createdBy("Demo")
                .modifiedBy("Demo")
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        return topicOnlineMapper.toTopicOnlineDTO(topicOnlineRepository.save(topicOnline));
    }

    @Override
    public TopicOnlineDTO edit(EditTopicOnline editTopicOnline) {
        TopicOnline topicOnline = topicOnlineRepository.findById(editTopicOnline.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_NOTFOUND));

        CourseOnline courseOnline = courseOnlineRepository.findById(editTopicOnline.getCourseOnlineId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOTFOUND));

        if (!topicOnline.getSlug().equals(editTopicOnline.getName().toLowerCase().replace(" ", "-"))) {
            TopicOnline topicOnlineExisting = topicOnlineRepository.findBySlug(editTopicOnline.getName().toLowerCase().replace(" ", "-"));
            if (topicOnlineExisting != null) throw new AppException(ErrorCode.COURSE_EXISTED);
        }
        topicOnline.setName(editTopicOnline.getName());
        topicOnline.setSlug(editTopicOnline.getName().toLowerCase().replace(" ", "-"));
        topicOnline.setOrderTop(editTopicOnline.getOrderTop());
        topicOnline.setCourseOnline(courseOnline);
        topicOnline.setModifiedBy("Demo");
        topicOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        return topicOnlineMapper.toTopicOnlineDTO(topicOnlineRepository.save(topicOnline));
    }

    @Override
    public void delete(Long[] ids) {
        log.info("Length: {}",ids.length);
        topicOnlineRepository.deleteAllById(List.of(ids));
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
