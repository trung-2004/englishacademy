package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.entities.Classes;

import java.util.List;

public interface ICourseOfflineService {
    List<CourseOfflineDTO> findAll();
    /*CourseOnlineDTO findBySlug(String slug);
    CourseOnlineDTO create(CreateCourseOnline model);
    CourseOnlineDTO edit(EditCourseOnline model);
    void delete(Long[] ids);*/
    List<CourseOfflineDTO> findByStudent(Long studentId);

    CourseOfflineDetail getDetail(String slug,Long StudentId);

}
