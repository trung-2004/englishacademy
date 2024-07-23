package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.admin.Menu;
import com.englishacademy.EnglishAcademy.dtos.admin.MenuItem;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorRevenueDTO;
import com.englishacademy.EnglishAcademy.entities.User;

import java.util.List;

public interface AdminService {
    List<Menu> getMenu(User currenUser);
    List<CourseOnlineMonthlyRevenueDTO> getCourseOnlineMonthlyRevenueLast12Months(User currenUser);
    double getCountCourseOnRevenue(User currenUser);
    double getCountCourseOfRevenue(User currenUser);
    double getCountTutorRevenue(User currenUser);
    List<CourseOnlineMonthlyRevenueDTO> getTutorMonthlyRevenueLast12Months(User currenUser);
    List<CourseOnlineRevenueDTO> getCourseOnlineTop10Revenue(User currenUser);
    List<TutorRevenueDTO> getTutorTop10Revenue(User currenUser);
    int getCountClasses(User currenUser);
    int getCountStaff(User currenUser);
    int getCountOnline(User currenUser);
    int getCountOffline(User currenUser);
}
