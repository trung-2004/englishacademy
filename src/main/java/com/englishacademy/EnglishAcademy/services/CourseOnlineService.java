package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.models.course_online.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.course_online.EditCourseOnline;

import java.util.List;

public interface CourseOnlineService {
    List<CourseOnlineDTO> findAll();
    List<CourseOnlineResponse> findAllByStudent(Long studentId);
    CourseOnlineDTO findBySlug(String slug);
    CourseOnlineDTO create(CreateCourseOnline model);
    CourseOnlineDTO edit(EditCourseOnline model);
    void delete(Long[] ids);
    CourseOnlineDetail getDetail(String slug);
    List<CourseOnlineDTO> getCourseTop6();
    List<CourseOnlineDTO> getCourseTopToeic(Integer score);
    List<CourseOnlineDTO> getCourseTopIelts(Integer score);
}
