package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDTO;
import com.englishacademy.EnglishAcademy.dtos.course_offline.CourseOfflineDetail;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.models.course_offline.CreateCourseOffline;
import com.englishacademy.EnglishAcademy.models.course_offline.EditCourseOffline;

import java.util.List;

public interface CourseOfflineService {
    List<CourseOfflineDTO> findAll();
    /*CourseOnlineDTO findBySlug(String slug);
    CourseOnlineDTO create(CreateCourseOnline model);
    CourseOnlineDTO edit(EditCourseOnline model);
    void delete(Long[] ids);*/
    List<CourseOfflineDTO> findByStudent(Long studentId);

    CourseOfflineDetail getDetail(String slug,Long StudentId);

    CourseOfflineDTO findBySlug(String slug);

    CourseOfflineDTO create(CreateCourseOffline model, User user);

    CourseOfflineDTO edit(EditCourseOffline model, User user);

    void delete(Long[] ids);

    CourseOfflineDetail getDetailTeacher(String slug, Long id);

    List<CourseOfflineDTO> findByUser(Long id, Long classId);
}
