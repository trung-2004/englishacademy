package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDetail;

import java.util.List;

public interface CourseOfflineService {
    List<CourseOfflineDTO> findAll();
    /*CourseOnlineDTO findBySlug(String slug);
    CourseOnlineDTO create(CreateCourseOnline model);
    CourseOnlineDTO edit(EditCourseOnline model);
    void delete(Long[] ids);*/
    List<CourseOfflineDTO> findByStudent(Long studentId);

    CourseOfflineDetail getDetail(String slug,Long StudentId);

}
