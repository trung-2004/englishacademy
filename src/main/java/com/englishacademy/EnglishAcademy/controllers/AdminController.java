package com.englishacademy.EnglishAcademy.controllers;

import com.englishacademy.EnglishAcademy.dtos.ResponseObject;
import com.englishacademy.EnglishAcademy.dtos.admin.Menu;
import com.englishacademy.EnglishAcademy.dtos.admin.MenuItem;
import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.course_online_student.CourseOnlineMonthlyRevenueDTO;
import com.englishacademy.EnglishAcademy.dtos.tutor.TutorRevenueDTO;
import com.englishacademy.EnglishAcademy.entities.Student;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("menu")
    public ResponseEntity<ResponseObject> getMenu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<Menu> menuItems = adminService.getMenu(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/course-online/monthly-revenue-last-12-months")
    public ResponseEntity<ResponseObject> getMonthlyRevenueLast12MonthsCourseOn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<CourseOnlineMonthlyRevenueDTO> menuItems = adminService.getCourseOnlineMonthlyRevenueLast12Months(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/course-offline/monthly-revenue-last-12-months")
    public ResponseEntity<ResponseObject> getMonthlyRevenueLast12MonthsCourseOff() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<CourseOnlineMonthlyRevenueDTO> menuItems = adminService.getCourseOfflineMonthlyRevenueLast12Months(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/course-online/top-10-revenue")
    public ResponseEntity<ResponseObject> getTop10RevenueCourseOn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<CourseOnlineRevenueDTO> menuItems = adminService.getCourseOnlineTop10Revenue(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/tutor/top-10-revenue")
    public ResponseEntity<ResponseObject> getTop10RevenueTutor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<TutorRevenueDTO> menuItems = adminService.getTutorTop10Revenue(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/tutor/monthly-revenue-last-12-months")
    public ResponseEntity<ResponseObject> getMonthlyRevenueLast12MonthsTuTor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<CourseOnlineMonthlyRevenueDTO> menuItems = adminService.getTutorMonthlyRevenueLast12Months(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/revenue/all")
    ResponseEntity<ResponseObject> getCountAllStudentStuding() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        double courseOnline = adminService.getCountCourseOnRevenue(currenUser);
        double courseOffline = adminService.getCountCourseOfRevenue(currenUser);
        double tutor = adminService.getCountTutorRevenue(currenUser);
        Map<String, Double> result = new HashMap<>();
        result.put("courseOnlineRevenue", courseOnline);
        result.put("courseOfflineRevenue", courseOffline);
        result.put("tutorRevenue", tutor);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", result)
        );
    }

    @GetMapping("/count/all")
    ResponseEntity<ResponseObject> getCountAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        int countClasses = adminService.getCountClasses(currenUser);
        int countStaff = adminService.getCountStaff(currenUser);
        int countOnline = adminService.getCountOnline(currenUser);
        int countOffline = adminService.getCountOffline(currenUser);
        Map<String, Integer> result = new HashMap<>();
        result.put("countClasses", countClasses);
        result.put("countStaff", countStaff);
        result.put("countOnline", countOnline);
        result.put("countOffline", countOffline);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", result)
        );
    }

}
