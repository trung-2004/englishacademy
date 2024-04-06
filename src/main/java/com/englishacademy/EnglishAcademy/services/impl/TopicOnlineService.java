package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.topicOnline.CourseOnlineTopicDetailResponse;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.entities.*;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.*;
import com.englishacademy.EnglishAcademy.services.ITopicOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TopicOnlineService implements ITopicOnlineService {
    @Autowired
    private TopicOnlineRepository topicOnlineRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOnlineRepository courseOnlineRepository;
    @Autowired
    private CourseOnlineStudentRepository courseOnlineStudentRepository;
    @Autowired
    private ItemOnlineStudentRepository itemOnlineStudentRepository;
    @Autowired
    private TestOnlineStudentRepository testOnlineStudentRepository;
    @Autowired
    private TopicOnlineMapper topicOnlineMapper;

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
