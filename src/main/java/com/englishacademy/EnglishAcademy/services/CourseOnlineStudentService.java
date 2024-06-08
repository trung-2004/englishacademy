package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineStudentDTO;
import com.englishacademy.EnglishAcademy.models.course_online_student.CreateCourseOnlineStudent;

public interface CourseOnlineStudentService {
    CourseOnlineStudentDTO buyCourse(CreateCourseOnlineStudent model, Long studentId);
    boolean checkCourseOnlineRegistered(String slug, Long studentId);
}
