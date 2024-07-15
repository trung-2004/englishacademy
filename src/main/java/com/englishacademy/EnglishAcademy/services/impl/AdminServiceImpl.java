package com.englishacademy.EnglishAcademy.services.impl;

import com.englishacademy.EnglishAcademy.dtos.admin.Menu;
import com.englishacademy.EnglishAcademy.dtos.admin.MenuItem;
import com.englishacademy.EnglishAcademy.entities.Role;
import com.englishacademy.EnglishAcademy.entities.User;
import com.englishacademy.EnglishAcademy.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Override
    public List<Menu> getMenu(User currenUser) {
        List<Menu> menus = new ArrayList<>();
        Set<Role> roles = convertAuthoritiesToRoles(currenUser.getAuthorities());

        if (roles.contains(Role.ADMIN)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems2 = new ArrayList<>();
            List<MenuItem> menuItems3 = new ArrayList<>();
            List<MenuItem> menuItems4 = new ArrayList<>();
            List<MenuItem> menuItems5 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "config.routes.dashboard", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Course Online", "config.routes.course_online", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Course Offline", "config.routes.course_offline", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Categories", "config.routes.category_list", "fas fa-stream"));
            menuItems3.add(new MenuItem("Entrance Test", "config.routes.entrance_test", "fas fa-window-restore"));
            menuItems4.add(new MenuItem("Booking", "config.routes.booking_list", "fas fa-calendar-alt"));
            menuItems5.add(new MenuItem("Profile", "config.routes.profile", "fas fa-user-shield"));
            menus.add(new Menu("Dashboard", menuItems1));
            menus.add(new Menu("Course & Lesson", menuItems2));
            menus.add(new Menu("Entrance Test", menuItems3));
            menus.add(new Menu("Teacher & Tutor", menuItems4));
            menus.add(new Menu("Information", menuItems5));
        } else if (roles.contains(Role.TEACHER)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems2 = new ArrayList<>();
            List<MenuItem> menuItems4 = new ArrayList<>();
            List<MenuItem> menuItems5 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "config.routes.dashboard", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Class", "config.routes.class_list_teacher", "fas fa-door-open"));
            menuItems4.add(new MenuItem("Booking", "config.routes.booking_list", "fas fa-calendar-alt"));
            menuItems5.add(new MenuItem("Profile", "config.routes.profile", "fas fa-user-shield"));
            menus.add(new Menu("Dashboard", menuItems1));
            menus.add(new Menu("Teacher & Tutor", menuItems4));
            menus.add(new Menu("Class", menuItems2));
            menus.add(new Menu("Information", menuItems5));
        } else if (roles.contains(Role.TRAINERS)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems5 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "config.routes.dashboard", "fas fa-book-reader"));
            menuItems5.add(new MenuItem("Profile", "config.routes.profile", "fas fa-user-shield"));
            menus.add(new Menu("Dashboard", menuItems1));
            menus.add(new Menu("Information", menuItems5));
        }
        return menus;
    }

    private Set<Role> convertAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());
    }
}
