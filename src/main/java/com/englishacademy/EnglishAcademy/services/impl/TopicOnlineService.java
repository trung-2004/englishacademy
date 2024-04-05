package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetailResponse;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.CourseOnlineStudent;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.exceptions.ErrorCode;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineStudentRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.repositories.TopicOnlineRepository;
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
    private TopicOnlineMapper topicOnlineMapper;

    @Override
    public List<TopicOnlineDetailResponse> findAllByCourseSlug(String slug, Long userId) {
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

        return topicOnlineDetailList;
    }
}
