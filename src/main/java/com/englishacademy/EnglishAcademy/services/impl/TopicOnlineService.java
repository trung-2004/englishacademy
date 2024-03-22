package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.dtos.topicOnline.TopicOnlineDetail;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.entities.ItemOnline;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.TopicOnline;
import com.englishacademy.EnglishAcademy.mappers.TopicOnlineMapper;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.ItemOnlineRepository;
import com.englishacademy.EnglishAcademy.repositories.StudentRepository;
import com.englishacademy.EnglishAcademy.repositories.TopicOnlineRepository;
import com.englishacademy.EnglishAcademy.services.ITopicOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicOnlineService implements ITopicOnlineService {
    @Autowired
    private TopicOnlineRepository topicOnlineRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseOnlineRepository courseOnlineRepository;
    @Autowired
    private TopicOnlineMapper topicOnlineMapper;

    @Override
    public List<TopicOnlineDetail> findAllByCourseSlug(String slug, Long userId) {
        CourseOnline courseOnline = courseOnlineRepository.findBySlug(slug);
        if (courseOnline == null) {
            throw new RuntimeException("Not Found");
        }
        Optional<Student> student = studentRepository.findById(userId);
        if (!student.isPresent()){
            throw new RuntimeException("User Not Found");
        }

        List<TopicOnline> topicOnlineList = topicOnlineRepository.findAllByCourseOnline(courseOnline);

        List<TopicOnlineDetail> topicOnlineDetailList = new ArrayList<>();

        for (TopicOnline topicOnline: topicOnlineList) {
            TopicOnlineDetail TopicOnlineDetail = topicOnlineMapper.toTopicOnlineAndStudentDetail(topicOnline, student.get());
            topicOnlineDetailList.add(TopicOnlineDetail);
        }

        topicOnlineDetailList.sort(Comparator.comparingInt(TopicOnlineDetail::getOrderTop));

        return topicOnlineDetailList;
    }
}
