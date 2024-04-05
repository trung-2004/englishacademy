package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.courseOffline.CourseOnlineResponse;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.dtos.courseOnline.CourseOnlineDetail;
import com.englishacademy.EnglishAcademy.models.courseOnline.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.models.courseOnline.EditCourseOnline;

import java.util.List;

public interface ICourseOnlineService {
    List<CourseOnlineDTO> findAll();
    List<CourseOnlineResponse> findAllByStudent(Long studentId);
    CourseOnlineDTO findBySlug(String slug);
    CourseOnlineDTO create(CreateCourseOnline model);
    CourseOnlineDTO edit(EditCourseOnline model);
    void delete(Long[] ids);

    CourseOnlineDetail getDetail(String slug);

}
