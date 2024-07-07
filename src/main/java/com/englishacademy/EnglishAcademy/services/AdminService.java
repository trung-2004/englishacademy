package com.englishacademy.EnglishAcademy.services;

import com.englishacademy.EnglishAcademy.dtos.admin.Menu;
import com.englishacademy.EnglishAcademy.dtos.admin.MenuItem;
import com.englishacademy.EnglishAcademy.entities.User;

import java.util.List;

public interface AdminService {
    List<Menu> getMenu(User currenUser);
}
